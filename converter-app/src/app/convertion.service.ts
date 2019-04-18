import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Formula } from './formula';
import { ConvertionResult } from './convertionResult';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin' : '*' })
};

@Injectable({ providedIn: 'root' })
export class ConvertionService {
  baseUrl:string = "http://localhost/converter";

  private formularUrl = '/formula';
  private convertionUrl = '/convert';

  constructor(
    private http: HttpClient) { }

  /** GET heroes from the server */
  getFormulas (): Observable<Formula[]> {
    return this.http.get<Formula[]>(this.baseUrl + this.formularUrl, httpOptions)
      .pipe(
        tap(_ => this.log('fetched formulas')),
        catchError(this.handleError<Formula[]>('getFormulas', []))
      );
  }

  convert(formula: Formula, inputValue: number): Observable<ConvertionResult> {
    return this.http.get<ConvertionResult>(this.baseUrl + this.convertionUrl + '/' + formula.fromType
     + '/' + formula.toType + '/' + inputValue, httpOptions)
      .pipe(
        tap(_ => this.log('converted')),
        catchError(this.handleError<ConvertionResult>('convert', {formula:formula, value: -1}))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    console.log(`HeroService: ${message}`);
  }
}
