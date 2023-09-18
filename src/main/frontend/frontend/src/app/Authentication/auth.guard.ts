import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { inject } from '@angular/core';



export const AuthGuard: CanActivateFn =  ( //Se ocupa de cuidar que si no estoy logeado no me deje acceder
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot) => {



  const authService = inject(AuthService);
  const router = inject(Router);

  authService?.isLoggedIn()? true: router?.navigate(['/login']); return false;}




