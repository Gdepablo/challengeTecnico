import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { compileNgModule } from '@angular/compiler';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotesService {
  private notesArray: any[] = [];
  private url: String = environment.apiUrl;
  private JSESSIONID: String = '';

  constructor(private http: HttpClient,
    private cookieService: CookieService) {
   }
   public login():any {
    return this.http.post(`${this.url}/login`, {
      username: environment.databaseUsername,
      password:environment.databasePassword
    }).subscribe( () => {

      const jsessionId = this.cookieService.get("JSESSIONID");
      jsessionId != '' ? this.JSESSIONID= jsessionId : jsessionId; //If de una sola linea
     console.log("Cookie JSESSIONID", jsessionId);}, (error) => {console.log('Error en la solicitud', error)}

    );

   }

   public getAllNotes(): Observable<Object> { //Notas del usuario logeado
    // If the JSESSIONID cookie exists, set it as a request header
    /*let headers = new HttpHeaders();
    if (this.JSESSIONID) {
      headers = headers.set('Cookie', `JSESSIONID=${this.JSESSIONID}`);
    }*/

    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(environment.databaseUsername + ':' +  environment.databasePassword),
      'Access-Control-Allow-Origin': '*' // Permite todas las solicitudes CORS desde cualquier origen
    });

    const requestOptions = {
      headers: headers,
      withCredentials: true, // Agrega esta opción para enviar las cookies en la solicitud CORS
      crossDomain: true // Agrega esta opción para indicar que es una solicitud CORS
    };

    this.login();
    return this.http.get<any>(`${this.url}/notes/active`,requestOptions);
   }
}
