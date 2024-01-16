import java.text.SimpleDateFormat;
import java.util.Date;

class PeopleQueue extends Thread    {
    private String[] names;
    private int passes = 0;
    PeopleQueue(String... names) {
        this.names = names;
    }

    @Override
    public void run() {
        for (int i = 0; i < names.length; i++) {
            passes++;
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            System.out.println("Сотрудник: " + names[i] +" || Время входа -- "+ formater.format(date));
            try {
                sleep(1000); // Задержка в 1 сек для наглядности многопоточности
            } catch (Exception e) {}
        }
        System.out.println("Сотрудников вошло: " + passes);
    }
}

public class user    {

    public static void main(String[] args) {
        // Создаем две очереди
        PeopleQueue entrance1 = new PeopleQueue("Карина","Сергей","Николай","Фердинанд","Василий","Карина","Сергей","Николай","Фердинанд","Василий");
        PeopleQueue entrance2 = new PeopleQueue("Ирина","Людмила","Алиса","Иван","Ольга","Ирина","Людмила","Алиса","Иван");

        System.out.println("Начало работы!");
        entrance1.start(); //1 поток
        entrance2.start(); //2 поток

    }
}