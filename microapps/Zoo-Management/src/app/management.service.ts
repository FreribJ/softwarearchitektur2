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

  putTour(tour: Tour) {
    return this.http.put<String>(this.host + this.port, tour);
  }
  putAttraction(attraction: Attraction) {
    return this.http.put<String>(this.host + this.port, attraction);
  }
  getAttraction(name: String) {
    return this.http
      .get<Attraction>(this.host + this.port + "/attraction/" + name);
  }


}
