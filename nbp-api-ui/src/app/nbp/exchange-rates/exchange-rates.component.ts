import {Component, OnInit} from '@angular/core';
import {NbpService} from "../nbp.service";
import {MaxMinExchangeRateDTO} from "../max-min-exchange-rate-dto";

@Component({
  selector: 'app-exchange-rates',
  templateUrl: './exchange-rates.component.html',
  styleUrls: ['./exchange-rates.component.css']
})
export class ExchangeRatesComponent implements OnInit {
  selectedTable: string = 'A';
  selectedOperation: number = 0;
  selectedCurrencyCode: string = 'THB';
  numberOfLastQuotations: number = 1;
  selectedDate: string;
  resultMinAndMax: MaxMinExchangeRateDTO;
  result: number;
  codes: string[] = []
  currentDate: string;

  constructor(private nbpService: NbpService) {
    this.currentDate = new Date().toISOString().split('T')[0];
  }

  ngOnInit(): void {
    this.getCodes();
  }

  getCodes() {
    if (this.codes.length === 0 && this.selectedTable === 'A') {
      this.nbpService.getCodesForAverageExchangeRates().subscribe(
        result => {
          this.codes = result;
        }
      )
    } else if (this.codes.length === 0 && this.selectedTable === 'C') {
      this.nbpService.getCodesForBuyAndSellRates().subscribe(
        result => {
          this.codes = result;
        }
      );
    }
  }


  onCalculate(): void {
    if (this.selectedOperation == 0) {
      this.nbpService.executeAverageExchangeRateByDate(this.selectedCurrencyCode, this.selectedDate)
        .subscribe(
          result => {
            this.result = result;
          },
          error => {
            this.result = -1;
          }
        );
    }
    if (this.selectedOperation == 1) {
      this.nbpService.executeMaxAndMinAverageValue(this.selectedCurrencyCode, this.numberOfLastQuotations)
        .subscribe(
          result => {
            this.resultMinAndMax = result;
          },
          error => {
            this.resultMinAndMax = undefined;
          }
        );
    }
    if (this.selectedOperation == 2) {
      this.nbpService.executeMajorDifferenceBetweenBuyAndSellRates(this.selectedCurrencyCode, this.numberOfLastQuotations)
        .subscribe(
          result => {
            console.log("RESULT: " + this.result)
            this.result = result;
          },
      error => {
            this.result = -2;
          }
        );
    }
  }

  onChangeTable() {
    this.result = undefined
    this.resultMinAndMax = undefined;
    this.codes = []
    if (this.selectedTable === 'A') {
      this.selectedOperation = 0;
    } else if (this.selectedTable === 'C') {
      this.selectedOperation = 2;
      this.selectedCurrencyCode = 'USD';
    }
    this.getCodes();
  }
}
