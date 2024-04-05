import java.util.function.BiConsumer;

public class PairDemo {
    public static void main(String[] args) {
        Pair<Integer, Integer> pair1 = Pair.of(123,456);
        //реализация ниже
        pair1.setAction((first,second)->{
            System.out.printf("Object's first field %s",first.toString());
            System.out.printf("\nObject's second field %s",second.toString());
        });
        /*
        Метод ничего не возвращает, и простым способом записать в другую переменную
        данные из аргументов консюмера я не смог(не особо и пытался).
        Поэтому остановился на том, что можно эти аргументы выводить на экран
         */
        System.out.println("ACTION PERFORMED");
        pair1.ifValuesPresentDo(pair1.getAction());

        //типы пары не важны, ибо поля объекта пустые
        Pair<Integer, Integer> pair2 = Pair.empty();
        System.out.println();
        System.out.println("EMPTY OBJECT");
        System.out.println(pair2);

        System.out.println("REFERENCE'S HASHCODE: "+pair1.hashCode()); //хеш ссылки
        System.out.println("SUM OF THE OBJECT'S FIELDS' HASHCODES: "+pair1.fieldsHashCode()); //сумма хешей полей

        Pair<String, Integer> pair3 = Pair.of("Cool",456);
        Pair<String, Integer> pair4 = Pair.of("Cool",456);
        System.out.println("COMPARING RESULT: ");
        System.out.println(pair3.equals(pair4));
    }
}

class Pair<T, U> {
    private T first;
    private U second;

    //Действие, которое будет делать ifPresent() (см. ниже)
    private BiConsumer<T, U> action;

    private Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    //получаем доступ к действию из мейна
    public BiConsumer<T, U> getAction() {
        return action;
    }

    //в мейне будет задаваться желаемое действие
    public void setAction(BiConsumer<T, U> action) {
        this.action = action;
    }

    /*
    по условию не было конкретного действия, которое бы тут реализовывалось,
    поэтому решил сделать возможность реализации действия в мейне,
    чтобы можно было (в теории) написать любой лямбдовый метод для
    этого консюмера
     */
    public void ifValuesPresentDo(BiConsumer<T, U> action){
        if (first!=null&&second!=null){
            action.accept(first,second);
        }
    }

    //переменные, чтобы метод не обращался постоянно к геттерам - оптимизация?
    public boolean equals(Pair<T, U> pair) {
        T leftF = this.getFirst();
        U leftS = this.getSecond();
        T rightF = pair.getFirst();
        U rightS = pair.getSecond();
        //если поля не пустые (не null)
        if (allPresent(pair)){
            //если типы полей совпадают
            if (sameClasses(pair)){
                //true если данные совпадают
                return leftF.equals(rightF)&&leftS.equals(rightS);
            }
        }
        return false;
    }

    //проверяем на нулевую переменную
    private boolean allPresent(Pair<T, U> pair){
        return this.getFirst()!=null&&
                this.getSecond()!=null&&
                pair.getFirst()!=null&&
                pair.getSecond()!=null;
    }

    //проверяем сопоставимые типы ((тип 1-й в 1 с тип 1-й в 2) и (тип 2-й в 1 с тип 2-й в 2))
    private boolean sameClasses(Pair<T, U> pair){
        return this.getFirst().getClass()==pair.getFirst().getClass()&&
                this.getSecond().getClass()==pair.getSecond().getClass();
    }

    //возращает пустой объект, типы пары-результата не важны, ибо поля пустые
    public static <T, U> Pair<T, U> empty(){
        return new Pair<>(null,null);
    }

    //возвращает сумму хешей полей объекта
    public int fieldsHashCode(){
        return this.getFirst().hashCode()+this.getSecond().hashCode();
    }

    //создаем новые объекты посредством обращения к приватному конструктору
    public static<T, U> Pair<T, U> of(T firstVal, U secondVal){
        return new Pair<>(firstVal,secondVal);
    }

    //если захочется вывести объект в консоль
    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}