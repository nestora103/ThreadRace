package ru.geekbrains.homework5.gubenkoDM;

import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static boolean flag;
    static {
        CARS_COUNT = 0;
        flag=true;
    }

    private Race race;
    private int speed;
    private String name;
    private int carsNum;

    public int getCarsNum() {
        return carsNum;
    }

    public String getName() {
        return name;
    }



    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        carsNum=CARS_COUNT;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
//            synchronized (MainClass.lock){
//                MainClass.lock.lock();
//                System.out.println("1");
//            }
            MainClass.lock.tryLock();

            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cb.await();
//            synchronized (MainClass.lock){
//                MainClass.lock.unlock();
//                System.out.println("2");
//            }
            if (flag){
                flag=false;
                MainClass.lock.unlock();
                System.out.println("2");
            }

        } catch (Exception e) {
            System.out.println("синх");
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {//начало прохождения препятствий авто-потоком
            race.getStages().get(i).go(this);
        }
    }
}

