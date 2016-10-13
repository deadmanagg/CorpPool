package com.example.corppool.model;

/**
 * Stores the time difference in terms of Years, months, days hours etc.
 * Format method will return in terms of Days, hours, for e.g
 * 1 Hour 23 min
 * Created by deepansh on 10/13/2016.
 */
public class TimeDifference {

    public long YEARS = 0;
    public long MONTH =0;
    public long DAYS= 0;
    public long HOURS= 0;
    public long MINUTES= 0;
    public long SECONDS= 0;

    private TimeDifference(final long YEARS,final long MONTH,final long DAYS,final long HOURS,final long MINUTES,final long SECONDS) {
        this.YEARS = YEARS;
        this.MONTH = MONTH;
        this.DAYS = DAYS;
        this.HOURS = HOURS;
        this.MINUTES = MINUTES;
        this.SECONDS = SECONDS;
    }

    public TimeDifference(){}
    /**
     * Builder to create instance of Time difference based on the required
     * params
     */
    public static class TimeDifferenceBuilder{

        private long YEARS= 0;
        private long MONTH= 0;
        private long DAYS= 0;
        private long HOURS= 0;
        private long MINUTES= 0;
        private long SECONDS= 0;

        public TimeDifferenceBuilder(final long min){
            this.MINUTES = min;
        }

        public TimeDifferenceBuilder Years(final long Years){
            this.YEARS = Years;
            return this;
        }

        public TimeDifferenceBuilder Months(final long Months){
            this.MONTH = Months;
            return this;
        }
        public TimeDifferenceBuilder Days(final long Days){
            this.DAYS = Days;
            return this;
        }
        public TimeDifferenceBuilder Hours(final long Hours){
            this.HOURS = Hours;
            return this;
        }

        public TimeDifferenceBuilder Seconds(final long Seconds){
            this.SECONDS = Seconds;
            return this;
        }

        public TimeDifference build(){

            return new TimeDifference(YEARS,MONTH,DAYS,HOURS,MINUTES,SECONDS);

        }
    }

    public String format(){
        String formattingTimeDiff ="";

        if(YEARS != 0){
            formattingTimeDiff+=YEARS+" years ";
        }

        if(MONTH != 0){
            formattingTimeDiff+=MONTH+" months ";
        }
        if(DAYS != 0){
            formattingTimeDiff+=DAYS+" days ";
        }
        if(HOURS != 0){
            formattingTimeDiff+=HOURS+" hrs ";
        }
        if(MINUTES != 0){
            formattingTimeDiff+=MINUTES+" min ";
        }
        if(SECONDS != 0){
            formattingTimeDiff+=SECONDS+" sec ";
        }

        return formattingTimeDiff;

    }
}
