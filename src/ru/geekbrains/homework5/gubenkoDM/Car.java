package ru.geekbrains.homework5.gubenkoDM;

import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;//счетчик для нумерации машин
    private static CyclicBarrier cb=new CyclicBarrier(MainClass.CARS_COUNT);//барьер для одновременного запуска гонки

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    //private int carsNum;

//   public int getCarsNum() {
//        return carsNum;
//    }

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
        //carsNum=CARS_COUNT;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cdlStart.countDown();//считаем сколько потоков уже выполнилось
            cb.await();//ждем другие машины для одновременного старта
            Thread.sleep(2);//делаем малеьнкую задержку для того чтобы сообщить о начале гонки
        } catch (Exception e) {
            System.out.println("Ошибка ожидания!");
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {//начало прохождения препятствий авто-потоком
            race.getStages().get(i).go(this);
        }
    }
}

