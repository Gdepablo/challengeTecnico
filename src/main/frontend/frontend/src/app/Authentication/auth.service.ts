import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginDetectado: EventEmitter<any> = new EventEmitter<any>

constructor(private http: HttpClient) {}

public login(username: String, password: String): Observable<Object> { //Notas del usuario logeado


  const headers = new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  });

  return this.http.post(`${environment.apiUrl}/login`, {'username': username,
  'password': password}, { headers });

 }

}
