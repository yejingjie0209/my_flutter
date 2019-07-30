import 'package:json_annotation/json_annotation.dart';

part 'weather.g.dart';


@JsonSerializable()
class Weather extends Object with _$WeatherSerializerMixin{

  @JsonKey(name: 'reason')
  String reason;

  @JsonKey(name: 'result')
  Result result;

  @JsonKey(name: 'error_code')
  int errorCode;

  Weather(this.reason,this.result,this.errorCode,);

  factory Weather.fromJson(Map<String, dynamic> srcJson) => _$WeatherFromJson(srcJson);

}


@JsonSerializable()
class Result extends Object with _$ResultSerializerMixin{

  @JsonKey(name: 'city')
  String city;

  @JsonKey(name: 'realtime')
  Realtime realtime;

  @JsonKey(name: 'future')
  List<WeatherFuture> future;

  Result(this.city,this.realtime,this.future,);

  factory Result.fromJson(Map<String, dynamic> srcJson) => _$ResultFromJson(srcJson);

}


@JsonSerializable()
class Realtime extends Object with _$RealtimeSerializerMixin{

  @JsonKey(name: 'temperature')
  String temperature;

  @JsonKey(name: 'humidity')
  String humidity;

  @JsonKey(name: 'info')
  String info;

  @JsonKey(name: 'wid')
  String wid;

  @JsonKey(name: 'direct')
  String direct;

  @JsonKey(name: 'power')
  String power;

  @JsonKey(name: 'aqi')
  String aqi;

  Realtime(this.temperature,this.humidity,this.info,this.wid,this.direct,this.power,this.aqi,);

  factory Realtime.fromJson(Map<String, dynamic> srcJson) => _$RealtimeFromJson(srcJson);

}


@JsonSerializable()
class WeatherFuture extends Object with _$FutureSerializerMixin{

  @JsonKey(name: 'date')
  String date;

  @JsonKey(name: 'temperature')
  String temperature;

  @JsonKey(name: 'weather')
  String weather;

  @JsonKey(name: 'wid')
  Wid wid;

  @JsonKey(name: 'direct')
  String direct;

  WeatherFuture(this.date,this.temperature,this.weather,this.wid,this.direct,);

  factory WeatherFuture.fromJson(Map<String, dynamic> srcJson) => _$FutureFromJson(srcJson);

}


@JsonSerializable()
class Wid extends Object with _$WidSerializerMixin{

  @JsonKey(name: 'day')
  String day;

  @JsonKey(name: 'night')
  String night;

  Wid(this.day,this.night,);

  factory Wid.fromJson(Map<String, dynamic> srcJson) => _$WidFromJson(srcJson);

}


