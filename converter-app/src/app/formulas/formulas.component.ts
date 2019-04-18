import { Component, OnInit } from '@angular/core';
import { Formula } from '../formula';
import { ConvertionService } from '../convertion.service';
import { ConvertionResult } from '../convertionResult';

@Component({
  selector: 'app-formulas',
  templateUrl: './formulas.component.html',
  styleUrls: ['./formulas.component.css']
})
export class FormulasComponent implements OnInit {

  formulas: Formula[] = [];

  convert(formula: Formula, inputValue: number): void {
    this.convertionService.convert(formula, inputValue)
    .subscribe(convertionResult => formula.result = convertionResult.value);
  }

  constructor(private convertionService: ConvertionService) { }

  ngOnInit() {
    this.getFormulas();
  }

  getFormulas(): void {
    this.convertionService.getFormulas()
    .subscribe(formulas => this.formulas = formulas);
  }

}
