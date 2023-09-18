import { Injectable, EventEmitter, Output } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotesService {
  //EL LOGIN DE SPRING SECURITY ACEPTA, PARA EL /login, QUERY PARAMS, Y PARA LO DEMAS BASIC AUTH POR ESO EL MAMBO.
  //Lo ideal seria tener definido el modelo en el front, pero no hay tiempo
  private notesArray: any[] = [];
  public observable: EventEmitter<any> = new EventEmitter();
  private JSESSIONID: String = '';


  constructor(private http: HttpClient,
    private cookieService: CookieService) {
   }

   public getAllNotes(username: String, password: String): Observable<Object> { //Notas del usuario logeado
    const headers = new HttpHeaders({'Authorization': 'Basic ' + btoa(username + ':' + password)})
    return this.http.get<any>('/notes/active',{headers});
   }

   public getJSESSIONID(): String { //Se crean getter y setter para poder compartir las notas entre componentes distintos
   return this.JSESSIONID;
   }

   public setNotes(notes: any[]) {
    this.notesArray = notes;
   }

   public getNotes() {
    return this.notesArray;
   }
}
