import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpStatusCode } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  template: ''
})
export class AuthComponent{
  returnUrl: string

  constructor(private authService: AuthService, private router: Router, private activatedRoute : ActivatedRoute) {
      // Obtienes la URL de retorno del query parameter 'returnUrl'
      this.returnUrl = this.activatedRoute.snapshot.queryParams['returnUrl'] || '/';
    }


    public login(username: string, password: string) {
      this.authService.login(username, password).subscribe(
        () => {
        this.router.navigate([this.returnUrl]);


        },
      );
    }
   }

