-- Бришење на претходни податоци за да нема дупликати при рестарт (опционално, зависи од ddl-auto)
TRUNCATE TABLE user_history CASCADE;
TRUNCATE TABLE code_puzzles CASCADE;

-- Вметнување на puzli со кодови каде решението е името на грешката или точниот збор што фали
INSERT INTO code_puzzles (snippet, solution) VALUES
    ('// Предизвик 1: Java
public class Main {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        // Кој исклучок (Exception) ќе фрли оваа линија?
        System.out.println(numbers[3]);
    }
}', 'ArrayIndexOutOfBoundsException');

INSERT INTO code_puzzles (snippet, solution) VALUES
    ('# Предизвик 2: Python
def calculate_average(nums):
    # Што ќе се случи ако листата "nums" е празна []?
    total = sum(nums)
    return total / len(nums)
', 'ZeroDivisionError');

INSERT INTO code_puzzles (snippet, solution) VALUES
    ('// Предизвик 3: Java
public class Test {
    public static void main(String[] args) {
        String name = null;
        // Која позната грешка ќе се појави тука?
        if(name.equals("Debuggle")) {
            System.out.println("True");
        }
    }
}', 'NullPointerException');

INSERT INTO code_puzzles (snippet, solution) VALUES
    ('// Предизвик 4: JavaScript
const person = { name: "Stefan" };
// Се обидуваме да пристапиме до својство на недефиниран објект
console.log(person.address.street);
// Која грешка ја фрла прелистувачот?', 'TypeError');

INSERT INTO code_puzzles (snippet, solution) VALUES
    ('// Предизвик 5: Java
public class Loop {
    public static void main(String[] args) {
        // Каков тип на јамка (infinite ...) е оваа?
        while(true) {
            System.out.println("Hello");
        }
    }
}', 'бесконечна');

INSERT INTO code_puzzles (snippet, solution) VALUES
    ('// Предизвик 6: SQL
SELECT * FROM users
-- Кој збор фали тука за да филтрираме по поени поголеми од 50?
_______ score > 50;', 'WHERE');

INSERT INTO code_puzzles (snippet, solution) VALUES
    ('// Предизвик 7: Java
public class Logic {
    public static void main(String[] args) {
        // Кој логички оператор фали за условот да биде ТУКА И ТАМУ (AND)?
        if (5 > 3 ___ 10 > 2) {
            System.out.println("И двата услови се точни!");
        }
    }
}', '&&');