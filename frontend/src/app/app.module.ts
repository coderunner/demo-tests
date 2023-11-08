import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { ViewPageComponent } from "./pages/view-page/view-page.component";
import { AddPageComponent } from "./pages/add-page/add-page.component";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { DisplayBooksComponent } from "./components/display-books/display-books.component";

@NgModule({
  declarations: [
    AppComponent,
    ViewPageComponent,
    AddPageComponent,
    DisplayBooksComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
