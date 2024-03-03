import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Attraction, Metadata, Tour} from "./entites";

@Injectable({
  providedIn: 'root'
})
export class ManagementService {
  host = "http://192.168.0.215";
  port = ":8080";
  ad="9"
  constructor(private http: HttpClient) {

  }

  getMetadata() {
    return this.http.get<Metadata>(this.host + this.port + "/metadata")
  }

  getTour(name: String) {
    return this.http.get<Tour>(this.host + this.port + "/tour/" + name);
  }

  putTour(tour: Tour) {
    return this.http.get<String>(this.host + this.port + "/tours/" + name);
  }
  putAttraction(tour: Tour) {
    return this.http.get<String>(this.host + this.port + "/tours/" + name);
  }


  getAttraction(name: String) {
    return this.http
      .get<Attraction>(this.host + this.port + "/attraction/" + name);
  }


}

