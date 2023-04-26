import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MaxMinExchangeRateDTO} from "./max-min-exchange-rate-dto";

@Injectable({
  providedIn: 'root'
})
export class NbpService {

  public codesForAverageExchangeRates: string[] = []
  public codesForBuyAndSellRates: string[] = []

  constructor(private httpClient: HttpClient) {}

  getCodesForAverageExchangeRates() {
    return this.httpClient.get<string[]>(`${this.apiUrl}/A/codes`);
  }

  getCodesForBuyAndSellRates() {
    return this.httpClient.get<string[]>(`${this.apiUrl}/C/codes`);
  }

  private apiUrl: string = "http://localhost:8080/api/exchange-rate";


  executeAverageExchangeRateByDate(currencyCode: string, date: string) {
    const url = `${this.apiUrl}/${currencyCode}/${date}`;
    console.log("URL: " + url)
    return this.httpClient.get<number>(url);
  }

  executeMaxAndMinAverageValue(currencyCode: string, numberOfLastQuotations: number) {
    const url = `${this.apiUrl}/${currencyCode}/max-min-average-value`;
    const params = {
      numberOfLastQuotations: numberOfLastQuotations
    }
    console.log("URL: " + url)
    return this.httpClient.get<MaxMinExchangeRateDTO>(url, {params});
  }

  executeMajorDifferenceBetweenBuyAndSellRates(currencyCode: string, numberOfLastQuotations: number) {
    const url = `${this.apiUrl}/${currencyCode}/major-rate-difference`;
    const params = {
      numberOfLastQuotations: numberOfLastQuotations
    }
    console.log("URL: " + url)
    return this.httpClient.get<number>(url, {params});
  }
}
