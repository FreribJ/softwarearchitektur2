import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Attraction, Metadata, Tour} from "./entites";

@Injectable({
  providedIn: 'root'
})
export class ManagementService {
  host = "http://localhost";
  port = ":8081";
  constructor(private http: HttpClient) {

  }

  getMetadata() {
    return this.http.get<Metadata>(this.host + this.port + "/metadata")
  }

  putMetadata(metadata: Metadata) {
    return this.http.put<any>(this.host + this.port, metadata);
  }

  getTour(name: String) {
    return this.http.get<Tour>(this.host + this.port + "/tour/" + name);
  }

  putTour(tour: Tour, name: String) {
    return this.http.put<any>(this.host + this.port+"/tour/" + name, tour);
  }
  putAttraction(attraction: Attraction, name: String) {
    return this.http.put<any>(this.host + this.port+ "/attraction/" + name, attraction);
  }
  postAttraction(attraction: Attraction) {
    return this.http.post<any>(this.host + this.port + "/attraction", attraction);
  }

  postTour(tour: Tour) {
    return this.http.post<any>(this.host + this.port + "/tour", tour);
  }
  getAttraction(name: String) {
    return this.http
      .get<Attraction>(this.host + this.port + "/attraction/" + name);
  }


  deleteAttraction(name: string) {
    return this.http.delete(this.host + this.port + "/attraction/" + name);
  }
  deleteTour(name: string) {
    return this.http.delete(this.host + this.port + "/tour/" + name);
  }
}
