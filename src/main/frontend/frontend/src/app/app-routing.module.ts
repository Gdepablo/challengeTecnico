import { NgModule } from '@angular/core';
import { RouterModule, Routes, provideRouter } from '@angular/router';
import { AuthComponent } from './Authentication/auth.component';
import { AppComponent } from './app.component';
//import { AuthGuard } from './Authentication/auth.guard';
import { AuthService } from './Authentication/auth.service';

const routes: Routes = [
  { path: 'login', component: AuthComponent },
  //{ path: '', component: AppComponent, canActivate: [AuthGuard] }, //Creo que va a haber que ponerle el canActivate a mano para que me proteja las rutas

];

@NgModule({
  imports: [RouterModule.forRoot(routes)], //TODO: Ver el login.
  exports: [RouterModule],
  providers: [AuthService]
})
export class AppRoutingModule { }
