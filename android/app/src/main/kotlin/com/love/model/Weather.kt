package com.love.model

/**
 * @author Jason
 * @description:
 * @date :2019-07-29 14:17
 */
class Weather : BaseResponse() {
    /**
     * reason : 查询成功!
     * result : {"city":"上海","realtime":{"temperature":"36","humidity":"50","info":"晴","wid":"00","direct":"西风","power":"1级","aqi":"43"},"future":[{"date":"2019-07-29","temperature":"29/36℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"南风"},{"date":"2019-07-30","temperature":"29/37℃","weather":"阴转多云","wid":{"day":"02","night":"01"},"direct":"南风"},{"date":"2019-07-31","temperature":"28/37℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"南风"},{"date":"2019-08-01","temperature":"28/35℃","weather":"晴转多云","wid":{"day":"00","night":"01"},"direct":"东南风"},{"date":"2019-08-02","temperature":"28/34℃","weather":"阴转小雨","wid":{"day":"02","night":"07"},"direct":"东南风"}]}
     * error_code : 0
     */
    var result: ResultBean? = null

    class ResultBean {
        /**
         * city : 上海
         * realtime : {"temperature":"36","humidity":"50","info":"晴","wid":"00","direct":"西风","power":"1级","aqi":"43"}
         * future : [{"date":"2019-07-29","temperature":"29/36℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"南风"},{"date":"2019-07-30","temperature":"29/37℃","weather":"阴转多云","wid":{"day":"02","night":"01"},"direct":"南风"},{"date":"2019-07-31","temperature":"28/37℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"南风"},{"date":"2019-08-01","temperature":"28/35℃","weather":"晴转多云","wid":{"day":"00","night":"01"},"direct":"东南风"},{"date":"2019-08-02","temperature":"28/34℃","weather":"阴转小雨","wid":{"day":"02","night":"07"},"direct":"东南风"}]
         */

        var city: String? = null
        var realtime: RealtimeBean? = null
        var future: List<FutureBean>? = null

        class RealtimeBean {
            /**
             * temperature : 36
             * humidity : 50
             * info : 晴
             * wid : 00
             * direct : 西风
             * power : 1级
             * aqi : 43
             */

            var temperature: String? = null
            var humidity: String? = null
            var info: String? = null
            var wid: String? = null
            var direct: String? = null
            var power: String? = null
            var aqi: String? = null
        }

        class FutureBean {
            /**
             * date : 2019-07-29
             * temperature : 29/36℃
             * weather : 多云
             * wid : {"day":"01","night":"01"}
             * direct : 南风
             */

            var date: String? = null
            var temperature: String? = null
            var weather: String? = null
            var wid: WidBean? = null
            var direct: String? = null

            class WidBean {
                /**
                 * day : 01
                 * night : 01
                 */

                var day: String? = null
                var night: String? = null
            }
        }
    }
}
