import { Injectable, EventEmitter, Output } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotesService {
  //EL LOGIN DE SPRING SECURITY ACEPTA, PARA EL /login, QUERY PARAMS, Y PARA LO DEMAS BASIC AUTH POR ESO EL MAMBO.
  //Lo ideal seria tener definido el modelo en el front, pero no hay tiempo


  constructor(private http: HttpClient)
   {}
   headers = new HttpHeaders({'Authorization': 'Basic ' + btoa(environment.databaseUsername + ':' + environment.databasePassword)})

   public getAllNotes(): Observable<any> { //Notas del usuario logeado

    return this.http.get<any>(`${environment.apiUrl}/notes/active`,{headers: this.headers});
   }

   public deleteNoteById(id: Number): Observable<any> {
    return this.http.delete(`${environment.apiUrl}/notes/delete/${id}`, {headers:this.headers})}

  public updateNote(id: Number, data: any): Observable<any> {
    return this.http.put(`${environment.apiUrl}/notes/update/${id}`,data, {headers:this.headers})
  }

  public getNoteById(id:Number): Observable<any> {
    return this.http.get(`${environment.apiUrl}/notes/${id}`, {headers:this.headers})
  }

  public newNote(data: any): Observable<any> {
    return this.http.post(`${environment.apiUrl}/notes/new`, data, {headers:this.headers})
  }

  public mapearJSON(data: any): any {
    return data.categories.tag.split(',').map((tag: any) => ({ tag }));

  }
}
