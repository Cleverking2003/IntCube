package com.example.intcube;

public class AxisCube {
    public Cubie[][][] cubiePos = new Cubie[3][3][3];

    public AxisCube() {
        //Up, Front Row
        cubiePos[0][0][0] = new Cubie(0,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('R','L'), new CubieColor('G','F')}, true, false);
        cubiePos[1][0][0] = new Cubie(1,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('G','F')}, false, true);
        cubiePos[2][0][0] = new Cubie(2,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('G','F'), new CubieColor('O','R')}, true, false);

        //Front, E Row
        cubiePos[0][0][1] = new Cubie(0,0,1,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('G','F')}, false, true);
        cubiePos[1][0][1] = new Cubie(1,0,1,
                new CubieColor[]{ new CubieColor('G','F')}, false, false, 'U');
        cubiePos[2][0][1] = new Cubie(2,0,1,
                new CubieColor[]{ new CubieColor('G','F'), new CubieColor('O','R')}, false, true);

        //Down, Front Row
        cubiePos[0][0][2] = new Cubie(0,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('R','L'), new CubieColor('G','F')}, true, false);
        cubiePos[1][0][2] = new Cubie(1,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('G','F')}, false, true);
        cubiePos[2][0][2] = new Cubie(2,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('G','F'), new CubieColor('O','R')}, true, false);

        //Up, S Row
        cubiePos[0][1][0] = new Cubie(0,1,0,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('Y','U')}, false, true);
        cubiePos[1][1][0] = new Cubie(1,1,0,
                new CubieColor[]{ new CubieColor('Y','U')}, false, false, 'U');
        cubiePos[2][1][0] = new Cubie(2,1,0,
                new CubieColor[]{ new CubieColor('Y','U'), new CubieColor('O','R')}, false, true);

        //E, S Row
        cubiePos[0][1][1] = new Cubie(0,1,1,
                new CubieColor[]{ new CubieColor('R','L')}, false, false, 'U');
        cubiePos[1][1][1] = new Cubie(1,1,1,
                new CubieColor[]{ new CubieColor('A','A')}, //Just giving random, non-legitimate values for color and direction
                false, false);
        cubiePos[2][1][1] = new Cubie(2,1,1,
                new CubieColor[]{ new CubieColor('O','R')}, false, false, 'U');

        //Down, S Row
        cubiePos[0][1][2] = new Cubie(0,1,2,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('W','D')}, false, true);
        cubiePos[1][1][2] = new Cubie(1,1,2,
                new CubieColor[]{ new CubieColor('W','D')}, false, false, 'U');
        cubiePos[2][1][2] = new Cubie(2,1,2,
                new CubieColor[]{ new CubieColor('W','D'), new CubieColor('O','R')}, false, true);

        //Up, Back Row
        cubiePos[0][2][0] = new Cubie(0,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('R','L'), new CubieColor('B','B')}, true, false);
        cubiePos[1][2][0] = new Cubie(1,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('B','B')}, false, true);
        cubiePos[2][2][0] = new Cubie(2,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('B','B'), new CubieColor('O','R')}, true, false);

        //E, Back Row
        cubiePos[0][2][1] = new Cubie(0,2,1,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('B','B')}, false, true);
        cubiePos[1][2][1] = new Cubie(1,2,1,
                new CubieColor[]{ new CubieColor('B','B')}, false, false, 'U');
        cubiePos[2][2][1] = new Cubie(2,2,1,
                new CubieColor[]{ new CubieColor('B','B'), new CubieColor('O','R')}, false, true);

        //Down, Back Row
        cubiePos[0][2][2] = new Cubie(0,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('R','L'), new CubieColor('B','B')}, true, false);
        cubiePos[1][2][2] = new Cubie(1,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('B','B')}, false, true);
        cubiePos[2][2][2] = new Cubie(2,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('B','B'), new CubieColor('O','R')}, true, false);
    }
}
