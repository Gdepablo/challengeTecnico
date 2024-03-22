import { Injectable, EventEmitter, Output } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotesService {
  bringNotes = new EventEmitter<any>

  constructor(private http: HttpClient) {}
   headers = new HttpHeaders({'Authorization': 'Basic ' + btoa(environment.databaseUsername + ':' + environment.databasePassword)})

   public getAllActive(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/notes/active`,{headers: this.headers});
   }

   public getAllArchived(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/notes/archived`,{headers: this.headers});
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

  public mapearJSON(data: any): Observable<any> {
    return data.categories.tag.split(',').map((tag: any) => ({ tag }));
  }

  public handleNoteStatus(note: any): Observable<any> {
    if(note.active == true)
      return this.http.put(`${environment.apiUrl}/notes/archive/${note.id}`, {}, {headers:this.headers}) 
    else {
      return this.http.put(`${environment.apiUrl}/notes/unarchive/${note.id}`, {}, {headers:this.headers})
    }
  }
}
