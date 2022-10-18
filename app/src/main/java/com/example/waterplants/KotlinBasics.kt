package com.example.waterplants
    // Testowa funkcja
    fun test (a: Int) : Int
    {
        return 0
    }

    fun main() {
        // var mozna zmieniac, val nie mozna zmieniac w programie
        // print nie ma konca linii, println ma koniec linii
        var name = "Rafal"
        val liczba: Int = 12
        var xd: Double = 21.37
        var czy:Boolean = true
        var R: Char = name[0]

        print("Hello " + name)
        // To jest to samo tylko tamplate
        print("Hello $name")

        when (liczba)
        {
            12 -> println("12")
            13 -> println("13")
            in 7..10 -> println("od 7 do 10")
            1, 2, 3 -> println("1, 2 lub 3")
            else -> println("inna liczba")
        }
        print("\n")

        for (i in 1 until 10)
        {
            println("$i")
        }
        print("\n")

        for (i in 10 downTo  1)
        {
            println("$i")
        }
        print("\n")

        // Jesli chce stworzyc zmienna np string pod ktora mozna podpisac tez null
        // To deklaruje ją jako
        var zmienna : String? = null
        // Jesli zmienna jest numm to to sie nie wykona
        zmienna?.let{println("Nie jest to null")}

        // Elvis operator, jesli zmienna jest nullem to przypisuje jej default value
        val elvis = zmienna ?: "default value"

        // Tworzenie obiektu klasy
        var osoba = Person("Rafal", 22)
        print(osoba.name)

        // Listy
        val stringList: List<String> = listOf("dupa", "cipa", "cyce")
        val mixedList: List<Any> = listOf(1, "cyce", 'A')

        // ArrayList
        val arrayList = ArrayList<String>()
        arrayList.add("One")
        arrayList.add("Two")

        // Dodawanie calej listy do array list
        var list:MutableList<String> = mutableListOf<String>()
        list.add("Three")
        list.add("Four")
        arrayList.addAll(list)

        println(arrayList)
    }

// Przykładowa klasa. Konstruktor jest jakby w argumentach klasy od razu.
class Person(var name: String, var age: Int)
{
    lateinit var freeTime: String // Te zmienna moge zainicjowac potem
    var hobby: String? = null
        // custom getter zwracajacy lowercase
        get()
        {
            return  field?.lowercase()
        }
        // Custom setter zawsze dajacy hobby na nothing
        set(value)
        {
            field = "Nothing"
        }

    // Dodatkowy konstruktor w ktorym mozna podac dodatkowo hobby
    constructor(name: String, age: Int, paramHobby: String) : this(name, age)
    {
        this.hobby = paramHobby
    }

    init{println("Person created")}
}