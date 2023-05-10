// Word class
abstract class Word(private val word: String, private val translation: String) {
    fun getWord(): String {
        return word
    }
    fun getTranslation(): String {
        return translation
    }
}

// VocabularyWord class
class VocabularyWord(word: String, translation: String, private val picture: String, private val category: String) : Word(word, translation) {
    fun getPicture(): String {
        return picture
    }
    fun getCategory(): String {
        return category
    }
}

// Phrase class
class Phrase(word: String, translation: String, private val audio: String) : Word(word, translation) {
    fun getAudio(): String {
        return audio
    }
}

// Language interface
interface Language {
    fun getWord(): Word
    fun getPhrase(): Phrase
}

// LanguageImpl class
class LanguageImpl(private val vocabulary: List<VocabularyWord>, private val phrases: List<Phrase>) : Language {
    override fun getWord(): Word {
        return vocabulary.random()
    }
    override fun getPhrase(): Phrase {
        return phrases.random()
    }
}

// App class
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
    val englishVocabulary = listOf(
        VocabularyWord("cat", "gato", "cat.jpg", "animals"),
        VocabularyWord("dog", "perro", "dog.jpg", "animals"),
        VocabularyWord("apple", "manzana", "apple.jpg", "food"),
        VocabularyWord("banana", "plátano", "banana.jpg", "food")
    )
    val spanishPhrases = listOf(
        Phrase("¿Cómo te llamas?", "What is your name?", "name.wav"),
        Phrase("Me gusta la pizza.", "I like pizza.", "pizza.wav")
    )
    val spanishLanguage = LanguageImpl(englishVocabulary, spanishPhrases)

    val app = LanguageLearningApp(spanishLanguage)

    app.addVocabularyWord(VocabularyWord("orange", "naranja", "orange.jpg", "food"))
    app.addPhrase(Phrase("¿Qué tal?", "How are you?", "howareyou.wav"))

    val randomWord = app.getWord()
    val randomPhrase = app.getPhrase()

    if (randomWord is VocabularyWord) {
        println("Vocabulary Word:")
        println("Word: ${randomWord.getWord()}")
        println("Translation: ${randomWord.getTranslation()}")
        println("Picture: ${randomWord.getPicture()}")
        println("Category: ${randomWord.getCategory()}")
    }
    if (randomPhrase is Phrase) {
        println("Phrase:")
        println("Word: ${randomPhrase.getWord()}")
        println("Translation: ${randomPhrase.getTranslation()}")
        println("Audio: ${randomPhrase.getAudio()}")
    }
}