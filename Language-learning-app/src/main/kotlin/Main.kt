abstract class Word(private val word: String, private val translation: String) {
    fun getWord(): String {
        return word
    }
    fun getTranslation(): String {
        return translation
    }
}

class VocabularyWord(word: String, translation: String, private val picture: String, private val category: String) : Word(word, translation) {
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
           // println("Selected word: ${word.getWord()}")
            return word
        }

        override fun getPhrase(): Phrase {
            val phrase = phrases.random()
            println("Selected phrase: ${phrase.getWord()}")
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
    //language object
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

    //app object
    val app = LanguageLearningApp(EnglishLanguage)

    app.addVocabularyWord(VocabularyWord("orange", "naranja", "orange.jpg", "food"))
    app.addPhrase(Phrase("¿Qué tal?", "How are you?", "howareyou.wav"))

    var word = app.getWord()
    var exit = false
    while (!exit) {
        printMenu()
        print("Enter your choice: ")
        when (readLine()?.toIntOrNull() ?: -1) {
            0 -> {
                exit = true
                println("Goodbye!")
            }
            1 -> {
                word = app.getWord()
                println("Selected word: ${word.getWord()}")
            }
            2 -> {
                val phrase = app.getPhrase()
                //println("Phrase: ${phrase.getWord()}")
                println("Translation: ${phrase.getTranslation()}")
                println("Audio: ${phrase.getAudio()}")
            }
            3 -> {
                println("Translation: ${word.getTranslation()}")
            }
            4 -> {
                val word1 = word as? VocabularyWord
                if (word1 != null) {
                    println("Category: ${word1.getPicture()}")
                } else {
                    println("Invalid option")
                }
            }
            5 -> {
                val word1 = word as? VocabularyWord
                if (word1 != null) {
                    println("Category: ${word1.getCategory()}")
                } else {
                    println("Invalid option")
                }
            }
            6 -> {
                word = app.getWord()
                println("Selected word: ${word.getWord()}")

            }
            else -> println("Invalid option")
        }
    }
}

private fun printMenu() {
    println("Available options\n press")
    println("0 - to quit\n" +
            "1 - random word\n" +
            "2 - random phrase\n" +
            "3 - translation\n" +
            "4 - picture\n" +
            "5 - category\n" +
            "6 - next word\n")
}