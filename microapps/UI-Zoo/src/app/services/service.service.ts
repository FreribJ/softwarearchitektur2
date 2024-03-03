import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Attraction, Metadata, Tour} from "./entities";
import {BehaviorSubject, fromEvent, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  host = "http://192.168.0.215";
  port = ":8080";
  modus = new BehaviorSubject<boolean>(false);
  modus$ = this.modus.asObservable();

  constructor(private http: HttpClient) {

  }

  getMetadata() {
    return this.http.get<Metadata>(this.host + this.port + "/metadata")
  }

  getAttractions() {
    return this.http.get<Attraction[]>(this.host + this.port + "/attractions");
  }

  getTours() {
    return this.http.get<Tour[]>(this.host + this.port + "/tours");
  }

  getTour(name: String) {
    return this.http.get<Tour>(this.host + this.port + "/tour/" + name);
  }


  getAttraction(name: String) {
    return this.http
      .get<Attraction>(this.host + this.port + "/attraction/" + name);
  }

  changeModus() {
    this.modus.next(!this.modus.value);
    localStorage.setItem('Modus', this.modus.value ? 'enabled' : 'disabled');
    console.log("Modus: " + this.modus.value);
  }

}

