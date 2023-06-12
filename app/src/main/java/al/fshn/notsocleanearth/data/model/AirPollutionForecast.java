package al.fshn.notsocleanearth.data.model;

import java.util.ArrayList;
import java.util.List;

public class AirPollutionForecast{
    public Coord coord;
    public ArrayList<List> list;

    public class Coord{
        public double lon;
        public double lat;
    }

    public class List{
        public Main main;
        public Components components;
        public int dt;

        public class Main{
            public int aqi;
        }

        public class Components{
            public double co;
            public double no;
            public double no2;
            public double o3;
            public double so2;
            public double pm2_5;
            public double pm10;
            public double nh3;
        }
    }
}
