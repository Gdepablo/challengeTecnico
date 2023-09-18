import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginDetectado: EventEmitter<any> = new EventEmitter<any>

constructor(private http: HttpClient,private cookie: CookieService) {}

public login(username: String, password: String): Observable<Object> { //Notas del usuario logeado


  const headers = new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  });

  return this.http.post('/login', {'username': username,
  'password': password}, { headers });

 }

 public isLoggedIn(): boolean {
 return this.cookie.check('JSESSIONID') //Si existe JSESSIONID quiere decir que esta logeado. Esto no es demasiado seguro pq te pueden crear una cookie JSESSIONID y entran igual
 //Cuando este todo terminado lo cambio a JWT. Me interesa terminar el front primero
 }

}
