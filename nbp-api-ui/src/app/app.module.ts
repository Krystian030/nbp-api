import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {ExchangeRatesComponent} from './nbp/exchange-rates/exchange-rates.component';
import {FormsModule} from "@angular/forms";
import {NbpHeaderComponent} from './nbp/nbp-header/nbp-header.component';
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    ExchangeRatesComponent,
    NbpHeaderComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
