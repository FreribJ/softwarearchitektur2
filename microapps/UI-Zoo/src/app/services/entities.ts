export interface Attraction {
name:String;
logo: any;
tags: String[];
nearestTourNames:String[];
description: String;
}
export interface Tour{
  description: String;
  name:String;
  logo: any;
  price:number;
  attractions:TourAttraction[];
}
export interface TourAttraction{
  attraction:String;
  begin: String;
  end: String;
}
export interface Metadata{
name: String;
logo: any;
address: String;
openingHours: OpeningHours[];
prices:Prices[];
description: String;
}

export interface Prices{
  category:String;
  price:number;
}

export interface OpeningHours{
  weekday:String;
  hours:String;
  info:String;
}

