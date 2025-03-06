package com.christianserwedevs.comprevo.Activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.Utilities.PageCurlTransformer;
import com.christianserwedevs.comprevo._Book.Book;
import com.christianserwedevs.comprevo._Book.BookPage;
import com.christianserwedevs.comprevo._Book.BookPageQuiz;
import com.christianserwedevs.comprevo._Book.BookPagerAdapter;
import com.christianserwedevs.comprevo._Book.QuizSet;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BookPagerAdapter adapter;
    private Book book;
    private int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        viewPager = findViewById(R.id.viewPager);
        List<BookPage> pages = new ArrayList<>();
        book = new Book("HOW THE WORLD WAS CREATED (PANAYAN)", "Folklore", pages);


        pages.add(new BookPage("The people of Panay, known for their rich cultural heritage and deep connection to nature, have passed down stories through generations, often weaving them into their daily lives and traditions. This particular tale originates from Panay and reflects the stories shared by the elders in the region about the creation of the world, especially those living near the mountain, who do not tire of relating, tells us that in the beginning there was no heaven or earth—only a bottomless deep and a world of mist. Everything was shapeless and formless—the earth, the sky, the sea, and the air were almost all mixed up.\n", R.drawable.how_1st));
        List<QuizSet> quizSets = new ArrayList<>();
        quizSets.add(new QuizSet(
                "What existed at the beginning of time according to the story?",
                new String[]{"A. A heaven and an earth.", "B. A bottomless deep and a world of mist.", "C. A sea and air.", "D. A sky and land."},
                2));
        quizSets.add(new QuizSet(
                "What were the earth, sky, sea, and air like at the beginning?\n",
                new String[]{"A. They were all separate and distinct.", "B. They were almost all mixed up.", "C. They were all the same.", "D. The They were all invisible."},
                2));
        pages.add(new BookPageQuiz(quizSets));
        pages.add(new BookPage("Then from the depth of this formless void, there appeared two gods, —Tungkung Langit and Alunsina. Just where the two deities came from it was not known. However, it was related that Tungkung Langit fell in love with Alunsina and, after so many years of courtship, they got married and had their abode in the highest realm of the eternal space where the water was constantly warm and the breeze was forever cool. It was in this place where order and regularity first took place.\n", R.drawable.how_2nd));



        adapter = new BookPagerAdapter(book, this, viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(new PageCurlTransformer());



    }
}
