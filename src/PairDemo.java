public class PairDemo {
    public static void main(String[] args) {
        //equals - сравнивает с другим объектом Pair, или с определенными значениями, как снизу?
        //equals для массивов должен сравнивать ссылки, или контент?
        // Если контент, то как дженерики преобразовать в массив?
        //hashcode() так же - для массивов работает по-другому
        //"фабричный" метод of? - пояснение
        //есть of() и ofNullable() - какой требуется?
        //пояснение по поводу конструктора
        //у BiConsumer есть метод accept(). Т.е. ifPresent() должен брать принятые консьюмером
        //переменные и проверять их на "null"?
        //empty() должен менять поля того же объекта на нуллы, или переписывать ссылку на новый
        //объект с нуллами?
        Pair<Integer, String> pair = new Pair<>(5, "string");
        System.out.println(pair);
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
        System.out.println(pair.equals(5, "string"));

        Pair<Character, Integer> pair2 = new Pair<>('C',123);
        System.out.println(pair2.equals('C',124));

        int[] q = {1,2,3};
        int[] w = q;
        Pair<int[], String> pair3 = new Pair<>(q,"qwer");
        System.out.println(pair3.equals(w,"qwer"));

        pair = pair.empty();
        System.out.println(pair);
    }
}

class Pair<T, U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    //equals() is not the same for arrays as Arrays.equals() - idk?
    public boolean equals(T val1, U val2) {
        return val1.equals(this.getFirst())&&val2.equals(this.getSecond());
    }

    public Pair<T, U> empty(){
        return new Pair<>(null,null);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}