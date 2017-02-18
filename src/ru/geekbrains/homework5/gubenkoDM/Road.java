package ru.geekbrains.homework5.gubenkoDM;

public class Road extends Stage {
    private static volatile boolean winFlag; //флаг для определения победителя

    static {
        winFlag=false;
    }

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            if (!winFlag && length==40){//условие победы
                winFlag=true;
                System.out.println(c.getName() + " WIN ");
            }
            MainClass.cdlG.countDown();//подсчет авто которые закончили гонку
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
