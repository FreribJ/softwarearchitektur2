export interface Attraction {
  name:String;
  logo: any;
  description: String;
  tags: String[];
}
export interface Tour{
  name:String;
  logo: any;
  price:number;
  description: String;
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
  description: String;
  prices:Prices[];
  openingHours: OpeningHours[];
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
