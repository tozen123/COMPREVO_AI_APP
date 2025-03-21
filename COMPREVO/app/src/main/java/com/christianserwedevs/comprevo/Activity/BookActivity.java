package com.christianserwedevs.comprevo.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.Utilities.PageCurlTransformer;
import com.christianserwedevs.comprevo._Book.Book;
import com.christianserwedevs.comprevo._Book.BookPage;
import com.christianserwedevs.comprevo._Book.BookPagerAdapter;
import com.christianserwedevs.comprevo._Book.QuizSet;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BookPagerAdapter adapter;
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        viewPager = findViewById(R.id.viewPager);

        String bookTitle = getIntent().getStringExtra("bookTitle");

        if (bookTitle == null) {
            Toast.makeText(this, "No book title received", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<BookPage> pages = new ArrayList<>();

        switch (bookTitle) {
            case "HOW THE WORLD WAS CREATED (PANAYAN)":
                pages = loadHowTheWorldWasCreatedPages();
                break;

            case "My Father Goes To Court":
               // pages = loadMyFatherGoesToCourtPages();
                break;

            case "Obesity (Essay)":
                //pages = loadObesityEssayPages();
                break;

            case "The God Stealer":
                //pages = loadTheGodStealerPages();
                break;

            case "The Philippines Battle Against Deforestation":
                //pages = loadDeforestationPages();
                break;

            default:
                Toast.makeText(this, "Book content not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
        }

        book = new Book(bookTitle, "Folklore", pages);




        adapter = new BookPagerAdapter(book, this, viewPager);
        viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);

        viewPager.setAdapter(adapter);
        RecyclerView recyclerView = (RecyclerView) viewPager.getChildAt(0);
        viewPager.setPageTransformer(new PageCurlTransformer());
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                BookPage selectedPage = book.getPages().get(position);

                if (selectedPage != null && selectedPage.hasQuiz() && !selectedPage.isQuizShown()) {
                    selectedPage.setQuizShown(true);

                    BookPagerAdapter.PageViewHolder holder =
                            (BookPagerAdapter.PageViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

                    if (holder != null) {
                        holder.itemView.post(() -> adapter.showQuizDialog(selectedPage, holder, position));
                    } else {
                        recyclerView.post(() -> {
                            BookPagerAdapter.PageViewHolder newHolder =
                                    (BookPagerAdapter.PageViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                            if (newHolder != null) {
                                newHolder.itemView.post(() -> adapter.showQuizDialog(selectedPage, newHolder, position));
                            }
                        });
                    }
                }
            }
        });

    }

    private List<BookPage> loadHowTheWorldWasCreatedPages()
    {
        List<BookPage> pages = new ArrayList<>();


        pages.add(new BookPage(
                "The people of Panay, known for their rich cultural heritage and deep connection to nature, have passed down stories through generations, often weaving them into their daily lives and traditions. This particular tale originates from Panay and reflects the stories shared by the elders in the region about the creation of the world, especially those living near the mountain, who do not tire of relating, tells us that in the beginning there was no heaven or earth—only a bottomless deep and a world of mist. Everything was shapeless and formless— the earth, the sky, the sea, and the air were almost all mixed up.\n",
                R.drawable.how_1st, null));




        List<QuizSet> quizSets_1 = new ArrayList<>();
        quizSets_1.add(new QuizSet(
                "What existed at the beginning of time according to the story?",
                new String[]{"A. A heaven and an earth.", "B. A bottomless deep and a world of mist.", "C. A sea and air.", "D. A sky and land."},
                2,
                "B is the answer. Because asdasdasd"
                ));
        quizSets_1.add(new QuizSet(
                "What were the earth, sky, sea, and air like at the beginning?",
                new String[]{"A. They were all separate and distinct.", "B. They were almost all mixed up.", "C. They were all the same.", "D. They were all invisible."},
                2,
                "B is the answer"
        ));
        pages.add(new BookPage(
                "Then from the depth of this formless void, there appeared two gods, — Tungkung Langit and Alunsina. Just where the two deities came from it was not known. However, it was related that Tungkung Langit fell in love with Alunsina and, after so many years of courtship, they got married and had their abode in the highest realm of the eternal space where the water was constantly warm and the breeze was forever cool. It was in this place where order and regularity first took place.\n",
                R.drawable.how_2nd, quizSets_1));




        List<QuizSet> quizSets_2 = new ArrayList<>();
        quizSets_2.add(new QuizSet(
                "Who were the two gods that appeared from the void?",
                new String[]{"A. Tungkung Langit and Alunsina", "B. Tungkung Langit and the sea breeze.", "C. Alunsina and the sea breeze.", "D. The sun and the moon."},
                2,
                "B is the answer"));
        pages.add(new BookPage(
                "Tungkung Langit was an industrious, loving, and kind god whose chief concern was how to impose order over the whole confused set-up of things. He assumed responsibility for the regular cosmic movement. On the other hand, Alunsina was a lazy, jealous, and selfish goddess whose only work was to sit by the window of their heavenly home, and amuse herself with her pointless thoughts. Sometimes, she would go down from the house, sit down by a pool near their doorstep and comb her long, jet-black hair all day long.\n",
                R.drawable.how_3rd, quizSets_2));




        List<QuizSet> quizSets_3 = new ArrayList<>();
        quizSets_3.add(new QuizSet(
                "What was Tungkung Langit's main concern?",
                new String[]{"A. To make Alunsina happy.", "B. To impose order over the universe.", "C. To create more gods.", "D. To make the world cold."},
                2,
                "B is the answer"));
        pages.add(new BookPage(
                "One day Tungkung Langit told his wife that he would be away from home for sometime to put an end to the chaotic disturbances in the flow of time and in the position of things. The jealous Alunsina, however, sent the sea breeze to spy on Tungkung Langit. This made the latter very angry upon knowing about it.",
                R.drawable.how_4th, quizSets_3));





        List<QuizSet> quizSets_4 = new ArrayList<>();
        quizSets_4.add(new QuizSet(
                "Why did Alunsina send the sea breeze to spy on Tungkung Langit?",
                new String[]{"A. Alunsina was jealous, and Tungkung Langit got angry.", "B. Alunsina wanted to leave, but Tungkung Langit stopped her.", "C. Tungkung Langit wanted to share his powers, but Alunsina refused.", "D. They could not agree on how to rule the world."},
                1,
                "A is the answer"));
        quizSets_4.add(new QuizSet(
                "Why was Tungkung Langit angry when he found out Alunsina sent the sea breeze?",
                new String[]{"A. He did not like the wind following him.", "B. He believed she should stay home and wait for him.", "C. He thought she was trying to control him.", "D. He was already upset about the chaotic disturbances."},
                3,
                "C is the answer"));
        pages.add(new BookPage(
                "Immediately after Tungkung Langit returned from his trip, he confronted Alunsina, accusing her of being ungodly for feeling jealous, there being no other creature living in the world except the two of them. This reproach was resented by Alunsina, and a quarrel between them followed. Tungkung Langit lost his temper. In this rage, he divested his wife of powers and drove her away. No one knew where Alunsina went; she merely disappeared.\n",
                R.drawable.how_5th, quizSets_4));






        List<QuizSet> quizSets_5 = new ArrayList<>();
        quizSets_5.add(new QuizSet(
                "Why did Tungkung Langit and Alunsina argue?",
                new String[]{"A. Alunsina was jealous, and Tungkung Langit got angry.", "B. Alunsina wanted to leave, but Tungkung Langit stopped her.", "C. Tungkung Langit wanted to share his powers, but Alunsina refused.", "D. They could not agree on how to rule the world."},
                1,
                "A is the answer"));
        quizSets_5.add(new QuizSet(
                "What happened to Alunsina after Tungkung Langit got angry?",
                new String[]{"A. She was given more powers.",
                        "B. She was driven away and lost her powers.",
                        "C. She stayed home and continued combing her hair.",
                        "D. She went to visit her friends."},
                2,
                "B is the answer"));
        quizSets_5.add(new QuizSet(
                "What can we infer about Tungkung Langit's feelings during their argument?",
                new String[]{"A. He was calm and understanding.",
                        "B. He was happy to see Alunsina stand up for herself.",
                        "C. He wanted Alunsina to leave from the start.",
                        "D. He was so angry that he acted without thinking."},
                4,
                "D is the answer"));
        pages.add(new BookPage(
                "Several days after Alunsina left, however, Tungkung Langit felt very lonely. He realized what he had done. Somehow, it was too late even to be sorry about the whole matter. The whole place, once vibrant with Alunsina‘s sweet voice, suddenly became cold and desolate. In the morning, when he woke up he would find himself alone and in the afternoon when he came home, he would feel the same loneliness creeping deep in his heart because there was no one to meet him at the doorstep or soothe the aching muscles of his arms.\n",
                R.drawable.how_6th, quizSets_5));





        List<QuizSet> quizSets_6 = new ArrayList<>();
        quizSets_6.add(new QuizSet(
                "How does the description of the place becoming \"cold and desolate\" reflect Tungkung Langit's emotional state?",
                new String[]{"A. It reflects his happiness and contentment.", "B. It reflects his sadness and loneliness.", "C. It reflects his anger and frustration.", "D. It reflects his indifference and neutrality."},
                2,
                "B is the answer"));
        quizSets_6.add(new QuizSet(
                "What could Tungkung Langit have done differently to prevent Alunsina from leaving?",
                new String[]{"A. He could have listened to her feelings and reassured her.", "B. He could have left her first before she had the chance to leave.", "C. He could have taken away more of her powers to make her stay.", "D. He could have ignored her jealousy and continued his work."},
                1,
                "A is the answer"));

        pages.add(new BookPage(
                "For months, Tungkung Langit lived in utter desolation. He could not find Alunsina, trying as hard as he could. And so, in his desperation, he decided to do something in order to forget his sorrows. For months and months he thought. His mind seemed pointless, his heart, weary, and sick. But he must have to do something about his loneliness.\n",
                R.drawable.how_7th, quizSets_6));



        List<QuizSet> quizSets_7 = new ArrayList<>();
        quizSets_7.add(new QuizSet(
                "Why did Tungkung Langit try so hard to find Alunsina?",
                new String[]{"A. He wanted to prove that he was stronger than her.",
                        "B. He regretted driving her away and wanted her back.",
                        "C. He needed her help to fix the world.",
                        "D. He was angry and wanted to punish her."},
                2,
                "B is the answer"));
        pages.add(new BookPage(
                "One day, while he was sailing across the regions of the clouds, a thought came to him. He would make a big basin of water below the sky so that he can see the image of his wife, if she were just somewhere in the regions above. And lo! The sea appeared. However, Alunsina was never seen. Maybe, in the wide world where no one knew where she had gone, she was too far away to be found, lost in a place where even her reflection could not appear.",
                R.drawable.how_8th, quizSets_7));


        List<QuizSet> quizSets_8 = new ArrayList<>();
        quizSets_8.add(new QuizSet(
                "What does Tungkung Langit’s attempt to see Alunsina’s reflection in the water tell us about his feelings?",
                new String[]{"A. He wanted to control her even from afar.",
                        "B. He deeply missed her and regretted losing her.",
                        "C. He was testing his powers to create something new.",
                        "D. He was curious if she was still watching over him."},
                2,
                "B is the answer"));
        pages.add(new BookPage(
                "After a long time, the somber sight of the lonely sea irritated Tungkung Langit. So he came down to the Middleworld and created the land; then he planted this with grasses, trees, and flowers. He took his wife‘s treasured jewels and scattered them in the sky, hoping that when Alunsina would see them she might be induced to return home. The goddess‘ necklace became the stars, her comb the moon, and her crown the  sun. However, despite all these Alunsina did not come back.",
                R.drawable.how_9th, quizSets_8));


        List<QuizSet> quizSets_9 = new ArrayList<>();
        quizSets_9.add(new QuizSet(
                "What do Tungkung Langit’s actions after Alunsina’s disappearance suggest about how people cope with loneliness?",
                new String[]{"A. People sometimes create or build new things to deal with their sadness.",
                        "B. People forget their past and move on quickly.",
                        "C. Loneliness makes people weaker and unable to do anything.",
                        "D. The only way to overcome loneliness is to find someone new"},
                1,
                "A is the answer"));
        pages.add(new BookPage(
                "And up to this time, the folks in Panay say that Tungkung Langit is alone in his palace in the skies. Sometimes, he would cry out of his pent-up emotions and his tears would fall down upon the earth. The people say that rain is Tungkung Langit‘s tears and that is why in some localities in the island of Panay, the first rain in May is received with much rejoicing and sacrifice. Incidentally, when it thunders hard, the old folks also say that it is Tungkung Langit sobbing, calling for his beloved Alunsina to come back – entreating her so hard that his voice thunders across the fields and countryside.\n",
                R.drawable.how_10th, quizSets_9));



        List<QuizSet> quizSets_10 = new ArrayList<>();
        quizSets_10.add(new QuizSet(
                "What lesson can be learned from Tungkung Langit and Alunsina’s story?",
                new String[]{"A. It is better to live alone than argue with others.",
                        "B. Power and anger make someone stronger.",
                        "C. People should never look for someone who leaves.",
                        "D. Love and trust are important in a relationship."},
                4,
                "D is the answer"));
        pages.add(new BookPage(
                "End of the Story",
                0, quizSets_10));

        return pages;
    }
//    private List<BookPage> loadMyFatherGoesToCourtPages()
//    {
//        List<BookPage> pages = new ArrayList<>();
//
//
//        pages.add(new BookPage(
//                "The people of Panay, known for their rich cultural heritage and deep connection to nature, have passed down stories through generations, often weaving them into their daily lives and traditions. This particular tale originates from Panay and reflects the stories shared by the elders in the region about the creation of the world, especially those living near the mountain, who do not tire of relating, tells us that in the beginning there was no heaven or earth—only a bottomless deep and a world of mist. Everything was shapeless and formless— the earth, the sky, the sea, and the air were almost all mixed up.\n",
//                R.drawable.how_1st, null));
//
//
//
//
//        List<QuizSet> quizSets_1 = new ArrayList<>();
//        quizSets_1.add(new QuizSet(
//                "What existed at the beginning of time according to the story?",
//                new String[]{"A. A heaven and an earth.", "B. A bottomless deep and a world of mist.", "C. A sea and air.", "D. A sky and land."},
//                2));
//        quizSets_1.add(new QuizSet(
//                "What were the earth, sky, sea, and air like at the beginning?",
//                new String[]{"A. They were all separate and distinct.", "B. They were almost all mixed up.", "C. They were all the same.", "D. They were all invisible."},
//                2));
//        pages.add(new BookPage(
//                "Then from the depth of this formless void, there appeared two gods, — Tungkung Langit and Alunsina. Just where the two deities came from it was not known. However, it was related that Tungkung Langit fell in love with Alunsina and, after so many years of courtship, they got married and had their abode in the highest realm of the eternal space where the water was constantly warm and the breeze was forever cool. It was in this place where order and regularity first took place.\n",
//                R.drawable.how_2nd, quizSets_1));
//
//
//        List<QuizSet> quizSets_10 = new ArrayList<>();
//        quizSets_10.add(new QuizSet(
//                "What lesson can be learned from Tungkung Langit and Alunsina’s story?",
//                new String[]{"A. It is better to live alone than argue with others.",
//                        "B. Power and anger make someone stronger.",
//                        "C. People should never look for someone who leaves.",
//                        "D. Love and trust are important in a relationship."},
//                4));
//        pages.add(new BookPage(
//                "End of the Story",
//                0, quizSets_10));
//
//        return pages;
//    }

}
