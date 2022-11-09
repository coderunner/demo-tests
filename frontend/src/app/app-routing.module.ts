import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddPageComponent } from './pages/add-page/add-page.component';
import { ViewPageComponent } from './pages/view-page/view-page.component';

const routes: Routes = [
  { path: 'add', component: AddPageComponent },
  { path: '**', component: ViewPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
