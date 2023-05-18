abstract class Word(private val word: String, private val translation: String) {
    fun getWord(): String {
        return word
    }

    fun getTranslation(): String {
        return translation
    }
}

class VocabularyWord(
    word: String,
    translation: String,
    private val picture: String,
    private val category: String
) : Word(word, translation) {
    fun getPicture(): String {
        return picture
    }

    fun getCategory(): String {
        return category
    }
}

class Phrase(word: String, translation: String, private val audio: String) : Word(word, translation) {
    fun getAudio(): String {
        return audio
    }
}

interface Language {
    fun getWord(): Word
    fun getPhrase(): Phrase

    class LanguageImpl(private val vocabulary: List<VocabularyWord>, private val phrases: List<Phrase>) : Language {
        private var currentIndex = -1

        override fun getWord(): Word {
            currentIndex++
            if (currentIndex >= vocabulary.size) {
                currentIndex = 0
            }
            val word = vocabulary[currentIndex]
            return word
        }

        override fun getPhrase(): Phrase {
            val phrase = phrases.random()
            return phrase
        }

        fun getProperty(property: Int): String? {
            val word = getWord() as? VocabularyWord ?: return null
            return when (property) {
                1 -> word.getTranslation()
                2 -> word.getPicture()
                3 -> word.getCategory()
                else -> null
            }
        }
    }
}

class LanguageLearningApp(private val language: Language) {
    private val vocabularyList = mutableListOf<VocabularyWord>()
    private val phraseList = mutableListOf<Phrase>()

    fun addVocabularyWord(vocabularyWord: VocabularyWord) {
        vocabularyList.add(vocabularyWord)
    }

    fun addPhrase(phrase: Phrase) {
        phraseList.add(phrase)
    }

    fun getWord(): Word {
        return language.getWord()
    }

    fun getPhrase(): Phrase {
        return language.getPhrase()
    }
}

fun main() {
    val AzerbaijanVocabulary = listOf(
        VocabularyWord("pişik", "cat", "cat.jpg", "animals"),
        VocabularyWord("it", "dog", "dog.jpg", "animals"),
        VocabularyWord("alma", "apple", "apple.jpg", "fruits/food"),
        VocabularyWord("banan", "banana", "banana.jpg", "fruits/food"),
        VocabularyWord("dəmir", "iron", "iron.jpg", "materials"),
        VocabularyWord("altın", "gold", "gold.jpg", "materials")
    )
    val AzerbaijanPhrases = listOf(
        Phrase("Adın nədir?", "What is your name?", "name.wav"),
        Phrase("Mənim 5 yaşım var", "I am 5 years old", "age.wav")
    )
    val EnglishLanguage = Language.LanguageImpl(AzerbaijanVocabulary, AzerbaijanPhrases)

    val app = LanguageLearningApp(EnglishLanguage)

    app.addVocabularyWord(VocabularyWord("orange", "naranja", "orange.jpg", "food"))
    app.addPhrase(Phrase("¿Qué tal?", "How are you?", "howareyou.wav"))

    var option = 0
    var word: Word? = null
    var exit = false

    while (!exit) {
        printMenu(option)
        print("Enter your choice: ")
        when (readLine()?.toIntOrNull() ?: -1) {
            0 -> {
                exit = true
                println("Goodbye!")
            }
            1 -> {
                option = 1
                word = app.getWord()
                println("Selected word: ${word.getWord()}")
            }
            2 -> {
                option = 2
                val phrase = app.getPhrase()
                word = phrase
                println("Selected phrase: ${phrase.getWord()}")
            }
            3 -> {
                if (word != null) {
                    println("Translation: ${word.getTranslation()}")
                } else {
                    println("No word or phrase selected")
                }
            }
            4 -> {
                if (option == 1 && word is VocabularyWord) {
                    println("Picture: ${(word as VocabularyWord).getPicture()}")
                } else if (option == 2 && word is Phrase) {
                    println("Audio: ${(word as Phrase).getAudio()}")
                } else {
                    println("Invalid option")
                }
            }
            5 -> {
                if (option == 1 && word is VocabularyWord) {
                    println("Category: ${(word as VocabularyWord).getCategory()}")
                } else {
                    println("Invalid option")
                }
            }
            6 -> {
                if (option == 1) {
                    word = app.getWord()
                    println("Selected word: ${word.getWord()}")
                } else {
                    println("Invalid option")
                }
            }
            else -> println("Invalid option")
        }
    }
}

private fun printMenu(option: Int) {
    println("Available options:")
    println("0 - to quit")
    println("1 - random word")
    println("2 - random phrase")
    if (option == 1 || option == 2) {
        println("3 - translation")
    }
    if (option == 1) {
        println("4 - picture")
        println("5 - category")
        println("6 - next word")
    } else if (option == 2) {
        println("4 - audio")
    }
    if (option == 1) {
        println("Selected option: Word")
    } else if (option == 2) {
        println("Selected option: Phrase")
    }
    println()
}
