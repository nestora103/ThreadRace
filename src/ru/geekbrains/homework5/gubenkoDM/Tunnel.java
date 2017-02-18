package ru.geekbrains.homework5.gubenkoDM;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private static Semaphore smp=new Semaphore(MainClass.CARS_COUNT/2);//ограничение потока для прохождение в туннеля


    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                System.out.println(c.getName() + " начал этап: " + description);
                smp.acquire();//авто зашло в туннель
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                smp.release();//авто вышло из тунеля
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
