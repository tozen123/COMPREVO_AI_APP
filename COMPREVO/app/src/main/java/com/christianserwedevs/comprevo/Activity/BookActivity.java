package com.christianserwedevs.comprevo.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.Utilities.ConfirmationDialog;
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

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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
                pages = loadMyFatherGoesToCourtPages();
                break;

            case "Obesity (Essay)":
                pages = loadObesityEssayPages();
                break;

            case "The God Stealer":
                pages = loadTheGodStealerPages();
                break;

            case "The Philippines Battle Against Deforestation":
                pages = loadDeforestationPages();
                break;

            default:
                Toast.makeText(this, "Book content not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
        }

        book = new Book(bookTitle, "Folklore", pages);




        adapter = new BookPagerAdapter(book, this, viewPager);
        viewPager.setOffscreenPageLimit(1);

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

                if (position == book.getPages().size() - 1 &&
                        selectedPage != null &&
                        selectedPage.getText().trim().equalsIgnoreCase("End of the Story")) {
                } else {
                    BookPagerAdapter.PageViewHolder holder =
                            (BookPagerAdapter.PageViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

                    if (holder != null) {
                        holder.finalPageDisplay.setVisibility(View.GONE);
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
                "B - is the correct answer because the story states that in the beginning, there was no heaven or earth—only a bottomless deep and a world of mist.",
                "A- is incorrect because the story says there was no heaven or earth in the beginning.\n\n" +
                        "B - is the correct answer because the story states that in the beginning, there was no heaven or earth—only a bottomless deep and a world of mist.\n\n" +
                        "C - is incorrect because the story does not mention a sea or air at the start. Everything was mixed up and had no shape.\n\n" +
                        "D - is incorrect because the story clearly says there was no heaven or earth, which means no sky or land.\n\n"

                ));
        quizSets_1.add(new QuizSet(
                "What were the earth, sky, sea, and air like at the beginning?",
                new String[]{"A. They were all separate and distinct.", "B. They were almost all mixed up.", "C. They were all the same.", "D. They were all invisible."},
                2,
                "B - is the correct answer because the earth, sky, sea, and air were almost mixed up, shapeless and formless.",
                "A - is incorrect because the story says everything was mixed up and had no clear shape.\n\n" +
                        "B - is the correct answer because the earth, sky, sea, and air were almost mixed up, shapeless and formless.\n\n" +
                        "C - is incorrect because the story does not say they were the same, only that they were mixed together.\n\n" +
                        "D - is  incorrect because the story does not say they could not be seen. It only says they had no shape or form.\n\n"
        ));
        pages.add(new BookPage(
                "Then from the depth of this formless void, there appeared two gods, — Tungkung Langit and Alunsina. Just where the two deities came from it was not known. However, it was related that Tungkung Langit fell in love with Alunsina and, after so many years of courtship, they got married and had their abode in the highest realm of the eternal space where the water was constantly warm and the breeze was forever cool. It was in this place where order and regularity first took place.\n",
                R.drawable.how_2nd, quizSets_1));




        List<QuizSet> quizSets_2 = new ArrayList<>();
        quizSets_2.add(new QuizSet(
                "Who were the two gods that appeared from the void?",
                new String[]{"A. Tungkung Langit and Alunsina", "B. Tungkung Langit and the sea breeze.", "C. Alunsina and the sea breeze.", "D. The sun and the moon."},
                1,
                "A - is the correct answer because the story mentions that the two gods who appeared from the void were Tungkung Langit and Alunsina.",
                "A - is the correct answer because the story mentions that the two gods who appeared from the void were Tungkung Langit and Alunsina.\n\n" +
                        "B - is incorrect because the sea breeze is not a god. It was only mentioned later in the story when Alunsina sent it to spy on Tungkung Langit.\n\n" +
                        "C - is incorrect because Alunsina was a goddess, but the sea breeze was not a god. The story only mentions two gods: Tungkung Langit and Alunsina.\n\n" +
                        "D - is incorrect because the sun and the moon were not mentioned as gods who appeared from the void.\n\n"

        ));
        pages.add(new BookPage(
                "Tungkung Langit was an industrious, loving, and kind god whose chief concern was how to impose order over the whole confused set-up of things. He assumed responsibility for the regular cosmic movement. On the other hand, Alunsina was a lazy, jealous, and selfish goddess whose only work was to sit by the window of their heavenly home, and amuse herself with her pointless thoughts. Sometimes, she would go down from the house, sit down by a pool near their doorstep and comb her long, jet-black hair all day long.\n",
                R.drawable.how_3rd, quizSets_2));




        List<QuizSet> quizSets_3 = new ArrayList<>();
        quizSets_3.add(new QuizSet(
                "What was Tungkung Langit's main concern?",
                new String[]{"A. To make Alunsina happy.", "B. To impose order over the universe.", "C. To create more gods.", "D. To make the world cold."},
                2,
                "B - is the correct answer because Tungkung Langit’s main concern was how to impose order over the whole confused set-up of things.",
                "A - is incorrect because the story focuses on Tungkung Langit's desire to bring order to the universe, not just to make Alunsina happy.\n" +
                        "B - is the correct answer because Tungkung Langit’s main concern was how to impose order over the whole confused set-up of things.\n\n" +
                        "C - is incorrect because the story does not mention Tungkung Langit wanting to create more gods. His focus was on organizing the world.\n\n" +
                        "D - is incorrect because the story does not say that Tungkung Langit wanted to make the world cold. Instead, he wanted to bring order.\n\n"

        ));
        pages.add(new BookPage(
                "One day Tungkung Langit told his wife that he would be away from home for sometime to put an end to the chaotic disturbances in the flow of time and in the position of things. The jealous Alunsina, however, sent the sea breeze to spy on Tungkung Langit. This made the latter very angry upon knowing about it.",
                R.drawable.how_4th, quizSets_3));





        List<QuizSet> quizSets_4 = new ArrayList<>();
        quizSets_4.add(new QuizSet(
                "Why did Alunsina send the sea breeze to spy on Tungkung Langit?",
                new String[]{"A. Alunsina was jealous, and Tungkung Langit got angry.", "B. Alunsina wanted to leave, but Tungkung Langit stopped her.", "C. Tungkung Langit wanted to share his powers, but Alunsina refused.", "D. They could not agree on how to rule the world."},
                1,
                "A - is the correct answer because Alunsina's jealousy led her to send the sea breeze to watch over Tungkung Langit, fearing he was unfaithful.",
                "A - is the correct answer because Alunsina's jealousy led her to send the sea breeze to watch over Tungkung Langit, fearing he was unfaithful.\n\n" +
                        "B - is incorrect because the story does not say that Alunsina was bored. It clearly states that she was jealous.\n\n" +
                        "C - is incorrect because, at this point in the story, Alunsina was not angry but jealous.\n\n" +
                        "D - is incorrect because the story does not mention Alunsina being sad. Instead, she was jealous and suspicious of Tungkung Langit.\n\n"


        ));
        quizSets_4.add(new QuizSet(
                "Why was Tungkung Langit angry when he found out Alunsina sent the sea breeze?",
                new String[]{"A. He did not like the wind following him.", "B. He believed she should stay home and wait for him.", "C. He thought she was trying to control him.", "D. He was already upset about the chaotic disturbances."},
                3,
                "C - is the correct answer because Tungkung Langit was angry because he felt that Alunsina did not trust him, despite his good intentions.",
                "A - is incorrect because his anger was not about the wind itself but about the lack of trust from Alunsina.\n\n" +
                        "B - is incorrect because the story does not say that Tungkung Langit wanted Alunsina to stay home and wait for him.\n\n" +
                        "C - is the correct answer because Tungkung Langit was angry because he felt that Alunsina did not trust him, despite his good intentions.\n\n" +
                        "D - is incorrect because Tungkung Langit's anger in this part of the story was caused by Alunsina's actions, not the chaotic disturbances.\n\n"

        ));
        pages.add(new BookPage(
                "Immediately after Tungkung Langit returned from his trip, he confronted Alunsina, accusing her of being ungodly for feeling jealous, there being no other creature living in the world except the two of them. This reproach was resented by Alunsina, and a quarrel between them followed. Tungkung Langit lost his temper. In this rage, he divested his wife of powers and drove her away. No one knew where Alunsina went; she merely disappeared.\n",
                R.drawable.how_5th, quizSets_4));






        List<QuizSet> quizSets_5 = new ArrayList<>();
        quizSets_5.add(new QuizSet(
                "Why did Tungkung Langit and Alunsina argue?",
                new String[]{"A. Alunsina was jealous, and Tungkung Langit got angry.", "B. Alunsina wanted to leave, but Tungkung Langit stopped her.", "C. Tungkung Langit wanted to share his powers, but Alunsina refused.", "D. They could not agree on how to rule the world."},
                1,
                "A - is the correct answer because their argument started because Alunsina’s jealousy upset Tungkung Langit, leading to a confrontation.",
                "A - is the correct answer because their argument started because Alunsina’s jealousy upset Tungkung Langit, leading to a confrontation.\n\n" +
                        "B - is incorrect because Alunsina did not want to leave at first. Tungkung Langit drove her away after their argument.\n\n" +
                        "C - is incorrect because the story does not mention Tungkung Langit offering to share his powers. Instead, he took Alunsina’s powers away.\n\n" +
                        "D - is incorrect because their fight was not about ruling the world but about Alunsina’s jealousy.\n\n"

        ));
        quizSets_5.add(new QuizSet(
                "What happened to Alunsina after Tungkung Langit got angry?",
                new String[]{"A. She was given more powers.",
                        "B. She was driven away and lost her powers.",
                        "C. She stayed home and continued combing her hair.",
                        "D. She went to visit her friends."},
                2,
                "B - is the correct answer because Tungkung Langit stripped Alunsina of her powers and cast her away, leaving her fate unknown.",
                "A - is incorrect because Tungkung Langit took away her powers, not gave her more.\n\n" +
                        "B - is the correct answer because Tungkung Langit stripped Alunsina of her powers and cast her away, leaving her fate unknown.\n\n" +
                        "C - is incorrect because she disappeared after being driven away.\n\n" +
                        "D - is incorrect because the story does not mention Alunsina having friends or visiting anyone.\n\n"

        ));
        quizSets_5.add(new QuizSet(
                "What can we infer about Tungkung Langit's feelings during their argument?",
                new String[]{"A. He was calm and understanding.",
                        "B. He was happy to see Alunsina stand up for herself.",
                        "C. He wanted Alunsina to leave from the start.",
                        "D. He was so angry that he acted without thinking."},
                4,
                "D - is the correct answer because his anger clouded his judgment, and he acted without considering the consequences of losing Alunsina.",
                "A - is incorrect because the story says he lost his temper, showing that he was not calm.\n\n" +
                        "B - is incorrect because he was angry, not happy, during their argument.\n\n" +
                        "C - is incorrect because he only sent her away after getting angry.\n\n" +
                        "D - is the correct answer because his anger clouded his judgment, and he acted without considering the consequences of losing Alunsina.\n\n"

        ));
        pages.add(new BookPage(
                "Several days after Alunsina left, however, Tungkung Langit felt very lonely. He realized what he had done. Somehow, it was too late even to be sorry about the whole matter. The whole place, once vibrant with Alunsina‘s sweet voice, suddenly became cold and desolate. In the morning, when he woke up he would find himself alone and in the afternoon when he came home, he would feel the same loneliness creeping deep in his heart because there was no one to meet him at the doorstep or soothe the aching muscles of his arms.\n",
                R.drawable.how_6th, quizSets_5));





        List<QuizSet> quizSets_6 = new ArrayList<>();
        quizSets_6.add(new QuizSet(
                "How does the description of the place becoming \"cold and desolate\" reflect Tungkung Langit's emotional state?",
                new String[]{"A. It reflects his happiness and contentment.", "B. It reflects his sadness and loneliness.", "C. It reflects his anger and frustration.", "D. It reflects his indifference and neutrality."},
                2,
                "B - is the correct answer because the once lively world became empty and lifeless, just as Tungkung Langit felt without Alunsina.",
                "A - is incorrect because Tungkung Langit was sad, not happy, after Alunsina left.\n\n" +
                        "B - is the correct answer because the once lively world became empty and lifeless, just as Tungkung Langit felt without Alunsina.\n\n" +
                        "C - is incorrect because his feelings after she left were more about sadness than anger.\n\n" +
                        "D - is incorrect because Tungkung Langit was not indifferent; he deeply missed Alunsina.\n\n"

                ));
        quizSets_6.add(new QuizSet(
                "What could Tungkung Langit have done differently to prevent Alunsina from leaving?",
                new String[]{"A. He could have listened to her feelings and reassured her.", "B. He could have left her first before she had the chance to leave.", "C. He could have taken away more of her powers to make her stay.", "D. He could have ignored her jealousy and continued his work."},
                1,
                "A - is the correct answer because if Tungkung Langit had reassured Alunsina instead of reacting angrily, their conflict might have been avoided.",
                "A - is the correct answer because if Tungkung Langit had reassured Alunsina instead of reacting angrily, their conflict might have been avoided.\n\n" +
                        "B - is incorrect because leaving first would not have solved their problems.\n\n" +
                        "C - is incorrect because taking away more of her powers would not have fixed their relationship.\n\n" +
                        "D - is incorrect because ignoring the issue would not have helped; communication was needed.\n\n"

        ));

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
                "B - is the correct answer because after Alunsina left, Tungkung Langit realized he missed her and wished he could bring her back.",
                "A - is incorrect because his search was not about proving strength, but about missing her.\n\n" +
                        "B - is the correct answer because after Alunsina left, Tungkung Langit realized he missed her and wished he could bring her back.\n\n" +
                        "C - is incorrect because Tungkung Langit created the world alone, without Alunsina’s help.\n\n" +
                        "D - is incorrect because Tungkung Langit was not angry anymore; he was sad and regretful.\n\n"

        ));
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
                "B - is the correct answer because creating the sea to see her reflection was his way of trying to reconnect with her, even if she was far away.",
                "A - is incorrect because he was not trying to control her; he was longing for her.\n\n" +
                        "B - is the correct answer because creating the sea to see her reflection was his way of trying to reconnect with her, even if she was far away.\n\n" +
                        "C - is incorrect because his goal was not to test his powers, but to see Alunsina again.\n\n" +
                        "D - is incorrect because the story does not say that Alunsina was watching over him.\n\n"

        ));
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
                "A - is the correct answer because instead of letting loneliness consume him, Tungkung Langit created the world as a way to cope with his sorrow.",
                "A - is the correct answer because instead of letting loneliness consume him, Tungkung Langit created the world as a way to cope with his sorrow.\n\n" +
                        "B - is incorrect because Tungkung Langit did not forget Alunsina quickly; he continued to miss her.\n\n" +
                        "C - is incorrect because Tungkung Langit did not become weak; instead, he created something new.\n\n" +
                        "D - is incorrect because Tungkung Langit never found someone new; he kept longing for Alunsina.\n\n"

        ));
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
                "D - is the correct answer because the story teaches that love and trust are important in relationships, as jealousy and anger can lead to loss.",
                "A - is incorrect because Tungkung Langit was lonely without Alunsina, showing that living alone is not always better.\n\n" +
                        "B - is incorrect because Tungkung Langit’s anger led to regret, not strength.\n\n" +
                        "C - is incorrect because Tungkung Langit spent a long time searching for Alunsina.\n\n" +
                        "D - is the correct answer because the story teaches that love and trust are important in relationships, as jealousy and anger can lead to loss.\n\n"

        ));
        pages.add(new BookPage(
                "End of the Story",
                0, quizSets_10));

        return pages;
    }
    private List<BookPage> loadTheGodStealerPages() {
        List<BookPage> pages = new ArrayList<>();

        pages.add(new BookPage(
                "Sam Christie, an American, and Philip Latak, a native from the province of Ifugao, work in the same office at an \"agency\" in Manila. Philip is Sam's assistant, and they are good friends. One day, Philip receives a message that his aging grandfather back home has fallen sick and that Philip needs to go home to see him. It happens that it's also Sam's last month in the Philippines before he heads home to the United States for a much-needed vacation leave. So, he decides to tag along with Philip for the trip to Ifugao. He also plans on buying an Ifugao god as a souvenir to take back home.\n",
                R.drawable.god_stealer_1, null));

        List<QuizSet> quizSets_1 = new ArrayList<>();

        quizSets_1.add(new QuizSet(
                "Where does Philip work with Sam?",
                new String[]{"A. A government office in Manila", "B. A business agency in Ifugao", "C. A private company in Baguio", "D. A tourism center in the U.S."},
                1,
                "A is the correct answer because the story states that Philip and Sam work in the same office at an \"agency\" in Manila.",
                "A is the correct answer because the story states that Philip and Sam work in the same office at an \"agency\" in Manila.\n\n" +
                        "B is incorrect because Philip works in Manila, not Ifugao.\n\n" +
                        "C is incorrect because there is no mention of Baguio in the story.\n\n" +
                        "D is incorrect because Sam and Philip work in Manila, not in the U.S.\n\n"));

        quizSets_1.add(new QuizSet(
                "What event prompts Philip to return to Ifugao?",
                new String[]{"A. A family celebration for his promotion", "B. An invitation from his brother Sadek", "C. News of his grandfather’s illness", "D. Sam’s request to visit the village"},
                3,
                "C is the correct answer because Philip receives a message that his grandfather is sick, which is why he decides to return.",
                "A is incorrect because the return is not for a celebration, but due to his grandfather’s illness.\n\n" +
                        "B is incorrect because Sadek does not invite Philip home.\n\n" +
                        "C is the correct answer because the story explicitly states that Philip’s grandfather is ill.\n\n" +
                        "D is incorrect because Sam’s desire to visit Ifugao is not the reason for Philip’s return.\n\n"));

        quizSets_1.add(new QuizSet(
                "What does Sam hope to bring back from the trip?",
                new String[]{"A. A handmade souvenir from Philip’s grandfather", "B. An Ifugao artifact displayed in the village", "C. A traditional Ifugao god for remembrance", "D. A symbolic item from the village chief"},
                3,
                "C is the correct answer because Sam expresses his desire to take an Ifugao god back as a souvenir.",
                "A is incorrect because Philip’s grandfather does not offer any souvenir, instead, he becomes angry when asked about the Ifugao god.\n\n" +
                        "B is incorrect because Sam specifically wants an Ifugao god, not just any artifact.\n\n" +
                        "C is the correct answer because Sam expresses his desire to take an Ifugao god back as a souvenir.\n\n" +
                        "D is incorrect because there is no mention of a village chief giving Sam anything.\n\n"));

        quizSets_1.add(new QuizSet(
                "Why does Sam decide to join Philip on the trip?",
                new String[]{"A. He is curious about Ifugao culture.", "B. He wants to see Philip’s family.", "C. He is looking for a special gift.", "D. He needs to take a break before returning home."},
                4,
                "D is the correct answer because the story mentions that it’s Sam’s last month in the Philippines and that he wants a trip before heading back to the U.S.",
                "A is incorrect because while Sam does experience Ifugao culture, his main reason for going is not curiosity.\n\n" +
                        "B is incorrect because Sam accompanies Philip, but he does not express a specific desire to meet his family.\n\n" +
                        "C is incorrect because while Sam wants an Ifugao god, his decision to join the trip is mainly for another reason.\n\n" +
                        "D is the correct answer because the story mentions that it’s Sam’s last month in the Philippines and that he wants a trip before heading back to the U.S.\n\n"));

        pages.add(new BookPage(
                "After a long bus ride, the two travelers reach Philip's village and after a brief visit with his older brother Sadek's home, the two head over to the house of the old man-Philip's 80-year-old grandfather.\n",
                R.drawable.god_stealer_2, quizSets_1));
        pages.add(new BookPage(
                "Philip asks his grandfather where he can get an Ifugao god for his American friend. The old man gets angry. The two leave the old man's house and head back to their lodging. On their second night at the town, a huge feast is held at the village for Philip. A ritual is held at the grandfather's house. Philip and Sam watch as the old man pours fresh animal blood on an Ifugao god. The ritual is the old man's way of thanking the god for letting his grandson come home. After the ritual at the old man's house, Sam and Philip join in on the festivities. Sam soon gets bored and decides to leave the party early and go to sleep.\n",
                R.drawable.god_stealer_3, null));

        List<QuizSet> quizSets_2 = new ArrayList<>();
        quizSets_2.add(new QuizSet(
                "How does Philip’s grandfather react when asked about the Ifugao god?",
                new String[]{"A. He calmly explains the tradition", "B. He gets angry and refuses to give one", "C. He agrees but only under strict conditions", "D. He tells Philip to ask his brother instead"},
                2,
                "B is the correct answer because the story explicitly states that Philip’s grandfather gets angry when asked about the Ifugao god.",
                "A is incorrect because the grandfather does not calmly explain, but gets angry.\n\n" +
                        "B is the correct answer because the story clearly mentions the grandfather’s anger.\n\n" +
                        "C is incorrect because the grandfather does not agree to give a god.\n\n" +
                        "D is incorrect because there is no mention of him suggesting Philip ask his brother.\n\n"));

        quizSets_2.add(new QuizSet(
                "What does the ritual at Philip’s grandfather’s house suggest?",
                new String[]{"A. The villagers want to show their hospitality", "B. The Ifugao gods are honored and given offerings", "C. The old man is preparing Philip for leadership", "D. The feast is the old man's way of thanking the god for letting his grandson come home"},
                4,
                "D is the correct answer because the story states that the ritual was to give thanks for Philip’s return.",
                "A is incorrect because the ritual is not about hospitality but about gratitude.\n\n" +
                        "B is incorrect because while Ifugao gods are honored in the ritual, the specific reason for this ritual is to give thanks for Philip’s return.\n\n" +
                        "C is incorrect because there is no indication that the ritual is about leadership.\n\n" +
                        "D is the correct answer because the story states that the ritual was to give thanks for Philip’s return.\n\n"));


        pages.add(new BookPage(
                "Past midnight, he wakes up to Philip coming home drunk and carrying an Ifugao god. Sam confronts Philip and tells him he shouldn't have stolen the god. He adds that Philip should return it and if he doesn't, Sam will return the god himself.",
                R.drawable.god_stealer_3_1, quizSets_2));

        List<QuizSet> quizSets_3 = new ArrayList<>();
        quizSets_3.add(new QuizSet(
                "Why does Sam want Philip to return the Ifugao god?",
                new String[]{"A. He sees that Philip is uncomfortable", "B. He fears the consequences of taking it", "C. He realizes the god holds deep meaning", "D. He does not want Philip to get in trouble"},
                3,
                "B is the correct answer because Sam warns Philip about the consequences of stealing the god.",
                "A is incorrect because Philip is drunk, and there is no discomfort expressed by Sam.\n\n" +
                        "B is incorrect because while Sam may later understand the god’s importance, his immediate concern is the consequences.\n\n" +
                        "C  is the correct answer because Sam immediately warns Philip about the dangers of stealing the god.\n\n" +
                        "D is incorrect because Sam’s primary concern is the consequences, not just getting in trouble.\n\n"));

        pages.add(new BookPage(
                "But Sam wakes up the next morning to see Philip hastily leaving to get back to the village. His grandfather had collapsed due to a heart attack and is dying. The next day, Sam is visited by Philip's older brother Sadek and is informed by him that their grandfather has passed away and that the cause of his death was the loss of his Ifugao god. Sadek also informs Sam that Philip isn't going back to Manila.\n",
                R.drawable.god_stealer_4, quizSets_3));

        List<QuizSet> quizSets_4 = new ArrayList<>();
        quizSets_4.add(new QuizSet(
                "What happens to Philip’s grandfather after the Ifugao god is stolen?",
                new String[]{"A. He gets sick and becomes weak", "B. He leaves the village without a word", "C. He suffers a heart attack and dies", "D. He forgives Philip but refuses to see him"},
                3,
                "C is the correct answer because the story explicitly states that he suffers a heart attack and dies after the god is stolen.",
                "A is incorrect because his death is sudden, not gradual.\n\n" +
                        "B is incorrect because he doesn’t leave the village.\n\n" +
                        "C is the correct answer because the grandfather dies after the god is stolen.\n\n" +
                        "D is incorrect because there is no mention of forgiveness before his death.\n\n"));

        quizSets_4.add(new QuizSet(
                "Why does Philip decide not to go back to Manila?",
                new String[]{"A. He feels responsible for his grandfather’s death", "B. He has always wanted to stay in the village", "C. He is afraid of what the villagers will do", "D. He wants to build a new home for his family"},
                1,
                "A is the correct answer because Philip blames himself for his grandfather’s death.",
                "A is the correct answer because Philip blames himself for his grandfather’s death.\n\n" +
                        "B is incorrect because before the incident, Philip was content living in Manila.\n\n" +
                        "C is incorrect because fear of the villagers is not the reason; it is guilt.\n\n" +
                        "D is incorrect because Philip does not mention wanting to build a home.\n\n"));

        quizSets_4.add(new QuizSet(
                "What does Philip’s act of carving a new Ifugao god represent?",
                new String[]{"A. His way of replacing what was lost", "B. His decision to create his own belief system", "C. His anger toward Sam for making him feel guilty", "D. His attempt to honor his grandfather’s traditions"},
                1,
                "A is the correct answer because he creates a new god as a way to make up for the stolen one.",
                "A is the correct answer because he creates a new god as a way to make up for the stolen one.\n\n" +
                        "B is incorrect because Philip does not create a new belief system.\n\n" +
                        "C is incorrect because he is angry at Sam, but that is not why he carves a new god.\n\n" +
                        "D is incorrect because while it may honor his grandfather, his main motivation is to replace what was lost.\n\n"));


        pages.add(new BookPage(
                        "Sam decides to visit Philip at the old man's house. But Philip meets him with anger and loathing. An argument ensues.\n",
                R.drawable.god_stealer_5, quizSets_4));
        pages.add(new BookPage(
                "The story ends with Sam leaving the old house and with Philip making another god to replace the one he has stolen. Philip warns him that if he tries to return the idol, the old man will kill him.",
                R.drawable.god_stealer_6, null));
        List<QuizSet> quizSets_5 = new ArrayList<>();
        quizSets_5.add(new QuizSet(
                "What does The God Stealer suggest about the relationship between modern life and traditional culture?",
                new String[]{
                        "A. Modernization often causes individuals to become disconnected from their cultural heritage.",
                        "B. Traditional beliefs and modern values can coexist peacefully without any conflict.",
                        "C. People should always choose between embracing modernity or preserving tradition.",
                        "D. Those who leave their culture behind can always return to it without any consequences."
                },
                1,
                "A is the correct answer because the story shows how Philip’s time in Manila has distanced him from his Ifugao roots, making him more willing to part with traditions that still hold deep meaning in his home village.",
                "A is the correct answer because the story shows how Philip’s time in Manila has distanced him from his Ifugao roots, making him more willing to part with traditions that still hold deep meaning in his home village.\n\n" +
                        "B is incorrect because the story highlights the conflict between modernity and tradition rather than a peaceful coexistence.\n\n" +
                        "C is incorrect because the story does not suggest that people must choose one or the other; instead, it shows the struggle of balancing both.\n\n" +
                        "D is incorrect because Philip’s experience shows that leaving behind one’s culture can have lasting consequences, not that people can always return without difficulty.\n\n"));

        quizSets_5.add(new QuizSet(
                "If you were Philip, how would you have handled the situation?",
                new String[]{
                        "A. Explained to Sam that Ifugao gods are sacred and cannot be taken away as souvenirs.",
                        "B. Asked for permission from the village elders before deciding whether to take the god.",
                        "C. Kept the Ifugao god hidden until he could find the right time to return it.",
                        "D. Apologized to his grandfather immediately and would find a way to make amends"
                },
                1,
                "A is the correct answer because explaining to Sam that Ifugao gods are sacred could have prevented the theft and its tragic consequences.",
                "A is the correct answer because explaining to Sam that Ifugao gods are sacred could have prevented the theft and its tragic consequences.\n\n" +
                        "B is incorrect because while asking for permission might have been a step, the Ifugao god was still considered sacred and likely would not have been given away.\n\n" +
                        "C is incorrect because hiding the god would not have resolved the issue and would not have prevented the grandfather’s reaction.\n\n" +
                        "D is incorrect because while apologizing is important, it alone would not have changed what had already happened.\n\n"));

        quizSets_5.add(new QuizSet(
                "What lesson can be learned from Sam’s experience in the story?",
                new String[]{
                        "A. It is important to bring back souvenirs from every place you visit.",
                        "B. Cultural appreciation should be done with deep respect and understanding.",
                        "C. Foreigners should always be allowed to take part in local traditions.",
                        "D. Friendship means supporting each other no matter what decisions are made."
                },
                2,
                "B is the correct answer because Sam learns that cultural appreciation requires respect and understanding, not just curiosity or collecting artifacts.",
                "A is incorrect because the lesson is not about collecting souvenirs but about respecting cultural significance.\n\n" +
                        "B is the correct answer because Sam learns that cultural appreciation requires respect and understanding, not just curiosity or collecting artifacts.\n\n" +
                        "C is incorrect because while foreigners can take part in traditions, the story shows that they should do so with deep respect.\n\n" +
                        "D is incorrect because friendship does not mean blindly supporting bad decisions. Sam challenges Philip’s actions because he knows they are wrong.\n\n"));

        quizSets_5.add(new QuizSet(
                "How does Philip’s final decision reflect the theme of cultural identity?",
                new String[]{
                        "A. It suggests that Philip is trying to forget the past and move on.",
                        "B. It proves that people who leave their cultural roots will always return in the end.",
                        "C. It shows that cultural heritage is something that can be easily restored.",
                        "D. It highlights the struggle of individuals torn between modern life and tradition."
                },
                4,
                "D is the correct answer because Philip is caught between his modern life in Manila and his traditional roots, and his final act of carving a new Ifugao god symbolizes his struggle to reconcile both.",
                "A is incorrect because Philip does not completely forget the past—his guilt drives him to try to restore what was lost.\n\n" +
                        "B is incorrect because the story does not suggest that people always return to their cultural roots without struggle or consequences.\n\n" +
                        "C is incorrect because the story does not imply that cultural heritage can be easily restored; Philip’s actions come from guilt and conflict.\n\n" +
                        "D is the correct answer because Philip is caught between his modern life in Manila and his traditional roots, and his final act of carving a new Ifugao god symbolizes his struggle to reconcile both.\n\n"));

        quizSets_5.add(new QuizSet(
                "How might the story have ended differently if Sam had never asked for an Ifugao god?",
                new String[]{
                        "A. Philip might have left Ifugao without questioning his cultural identity.",
                        "B. The village might have still experienced a loss due to other circumstances.",
                        "C. Philip’s grandfather might have lived longer, and Philip would have returned to Manila.",
                        "D. The story’s conflict would have shifted to a different issue regarding Philip’s identity."
                },
                3,
                "C is the correct answer because without the request, Philip would not have stolen the god, and his grandfather likely would not have suffered a fatal heart attack.",
                "A is incorrect because Philip was already somewhat disconnected from his cultural identity, but the theft forced him to confront it in a painful way.\n\n" +
                        "B is incorrect because while tragedies can happen for many reasons, the story specifically ties the grandfather’s death to the loss of the Ifugao god.\n\n" +
                        "C is the correct answer because without the request, Philip would not have stolen the god, and his grandfather likely would not have suffered a fatal heart attack.\n\n" +
                        "D is incorrect because while Philip’s identity is a major theme, the theft of the god is what drives the central conflict in the story.\n\n"));

        pages.add(new BookPage(
                "End of the Story",
                0, quizSets_5));

        return pages;
    }

    private List<BookPage> loadObesityEssayPages(){
        List<BookPage> pages = new ArrayList<>();

        pages.add(new BookPage(
                "Consumption of processed and convenience foods and our dependence on the car have led to an increase in obesity and reduction in the fitness level of the adult population. In some countries, especially industrialized ones, the number of obese people can amount to one third of the population (WHO, 2015). This is significant as obesity and poor fitness lead to a decrease in life expectancy, and it is therefore important for individuals and governments to work together to tackle this issue and improve their citizens' diet and fitness. People nowadays would prefer to ride instead of walking.\n",
                R.drawable.obesity_1st, null));


        List<QuizSet> quizSets_1 = new ArrayList<>();
        quizSets_1.add(new QuizSet(
                "As stated in the narrative, what has led to an increase in obesity and reduced fitness levels?",
                new String[]{"A. Lack of access to healthcare.", "B. Increased stress and mental health issues.", "C. Consumption of processed foods and dependence on cars.", "D. Overpopulation in urban areas."},
                3,
                "C is the correct answer because the passage states that the consumption of processed and convenience foods, along with a reliance on cars instead of walking, has contributed to rising obesity rates and declining fitness levels.",
                "A is incorrect because the passage focuses on diet and physical activity as the main causes of obesity, not medical care.\n\n" +
                        "B is incorrect because while stress and mental health issues can contribute to weight gain, the passage does not mention them as primary causes.\n\n" +
                        "C is the correct answer because the passage states that the consumption of processed and convenience foods, along with a reliance on cars instead of walking, has contributed to rising obesity rates and declining fitness levels.\n\n" +
                        "D is incorrect because while overpopulation might affect food availability and living conditions, the passage does not cite it as a reason for rising obesity rates.\n\n"));

        quizSets_1.add(new QuizSet(
                "What percentage of the population in some industrialized countries is obese, according to WHO (2015)?",
                new String[]{"A. One-fourth", "B. One-third.", "C. One-half", "D. One-fifth"},
                2,
                "B is the correct answer because the passage mentions that according to WHO (2015), in some industrialized countries, the number of obese people can amount to one-third of the population.",
                "A is incorrect because the passage specifically states one-third, not one-fourth.\n\n" +
                        "B is the correct answer because the passage mentions that according to WHO (2015), in some industrialized countries, the number of obese people can amount to one-third of the population.\n\n" +
                        "C is incorrect because while obesity rates are high, the passage does not indicate that half of the population is obese.\n\n" +
                        "D is incorrect because one-fifth is lower than the percentage cited in the passage.\n\n"));




        pages.add(new BookPage(
                "Obesity and poor fitness decrease life expectancy. Overweight people are more likely to have serious illnesses such as diabetes and heart disease, which can result in premature death (Wilson, 2014). It is well known that regular exercise can reduce the risk of heart disease and stroke, which means that those with poor fitness levels are at an increased risk of suffering from those problems.\n",
                R.drawable.obesity_2nd, quizSets_1));


        List<QuizSet> quizSets_3= new ArrayList<>();
        quizSets_3.add(new QuizSet(
                "Which diseases are mentioned as being linked to obesity and poor fitness?",
                new String[]{"A. Cancer and asthma", "B. Diabetes and heart disease.", "C. Tuberculosis and pneumonia", "D. Arthritis and osteoporosis"},
                2,
                "B. Diabetes and heart disease",
                "A is incorrect because the passage does not mention them in relation to obesity.\n\n" +
                        "B is the correct answer because the passage states that overweight individuals are more likely to develop serious illnesses such as diabetes and heart disease. \n\n" +
                        "C is incorrect because tuberculosis and pneumonia are infectious diseases and not commonly linked to obesity.\n\n" +
                        "D is incorrect because osteoporosis is more related to bone density rather than obesity.\n\n"));

        pages.add(new BookPage(
                "Changes by individuals in their diet and their physical activity can increase life expectancy. There is a reliance today on the consumption of processed foods, which have a high fat sugar content. According to Peterson (2013), preparing their own foods, and consuming more fruits and vegetables, people could ensure that their diets are healthier and more balanced, which could lead to a reduction in obesity levels. However, organising such a change in diet and a reduction of food would need to be controlled by a dietician expert. This would further incur additional costs." +
                        "In order to improve fitness levels, people could choose to walk or ride a bicycle to work or to the shops rather than taking the car. They could also choose to walk up the stairs instead of taking the lift. These small changes could lead to a significant improvement in fitness levels.\n",
                R.drawable.obesity_3rd, quizSets_3));


        List<QuizSet> quizSets_4= new ArrayList<>();

        quizSets_4.add(new QuizSet(
                "What are some suggested ways individuals can improve their fitness levels?",
                new String[]{"A. Avoid all fats and sugars in their diet.", "B. Take more vitamins and supplements.", "C. Walk or ride a bicycle instead of driving.", "D. Exercising only on weekends."},
                2,
                "C. Walk or ride a bicycle instead of driving.",
                "A is incorrect because the passage emphasizes balanced diets, not complete elimination of fats and sugars.\n\n" +
                        "B is incorrect because it is not mentioned in the passage. Additionally, while supplements can support health, these are not enough to be physically fit.\n\n" +
                        "C is the correct answer because the passage suggests that individuals can improve their fitness levels by making small lifestyle changes, such as walking or riding a bicycle instead of driving. These activities increase physical activity that enhances overall fitness.\n\n" +
                        "D is incorrect because fitness improvement requires consistent activity, not just weekend exercise.\n\n"));


        quizSets_4.add(new QuizSet(
                "Why might processed foods contribute to obesity?",
                new String[]{"A. They contain high levels of fat and sugar.", "B. They are only available in fast-food restaurants.", "C. They spoil more quickly than fresh foods.", "D. They require longer preparation time."},
                1,
                "A. They contain high levels of fat and sugar.",
                "A is the correct answer because the passage states that processed foods have a high fat and sugar content, which can contribute to obesity. Excessive consumption of these unhealthy ingredients leads to weight gain and health risks.\n\n" +
                        "B is incorrect because processed foods are sold in various places, not just in fast-food restaurants.\n\n" +
                        "C is incorrect because processed foods actually last longer due to preservatives.\n\n" +
                        "D is incorrect because processed foods are designed to be quick and easy to prepare.\n\n"));

        pages.add(new BookPage(
                "Governments could also implement initiatives to improve their citizens' eating and exercise habits. Jones (2011) argues that this could be done through education, for example by adding classes about healthy diet and lifestyles." +
                        "Education would be implemented in high school and would have a preventative effect on the younger generations rather than a cure for the obese older generation.",
                R.drawable.obesity_4th, quizSets_4));





        List<QuizSet> quizSets_5= new ArrayList<>();

        quizSets_5.add(new QuizSet(
                "What does Jones (2011) suggest as a way for governments to improve citizens’ eating and exercise habits?",
                new String[]{
                        "A. Increase gym membership subsidies.",
                        "B. Implement education on healthy lifestyles.",
                        "C. Offer free medical checkups.",
                        "D. Reduce the prices of organic foods."
                },
                2,
                "B is the correct answer because the passage states that Jones (2011) suggests education as a way for governments to improve citizens' eating and exercise habits. Specifically, adding classes about healthy diets and lifestyles in high school could help promote better habits from a young age.",
                "A is incorrect because the passage does not mention subsidies for gym memberships.\n\n" +
                        "B is the correct answer because the passage states that Jones (2011) suggests education as a way for governments to improve citizens' eating and exercise habits. Specifically, adding classes about healthy diets and lifestyles in high school could help promote better habits from a young age.\n\n" +
                        "C is incorrect because Jones (2011) does not specifically mention medical checkups.\n\n" +
                        "D is incorrect because while this may encourage healthier eating, the passage focuses on education as a key solution.\n\n"));

        quizSets_5.add(new QuizSet(
                "Why might education on healthy lifestyles be more effective for younger generations?",
                new String[]{
                        "A. Younger people are more open to change and lifelong habits are formed early.",
                        "B. Older generations are not interested in learning new information.",
                        "C. Schools have more resources to influence lifestyle choices than workplaces.",
                        "D. Government initiatives are only targeted at students."
                },
                1,
                "A is the correct answer because the passage explains that education on healthy lifestyles would have a preventative effect on younger generations rather than serving as a cure for obesity in older individuals. This is because younger people are more open to change, and the habits they develop early in life are more likely to persist into adulthood.",
                "A is the correct answer because the passage explains that education on healthy lifestyles would have a preventative effect on younger generations rather than serving as a cure for obesity in older individuals. This is because younger people are more open to change, and the habits they develop early in life are more likely to persist into adulthood.\n\n" +
                        "B is incorrect because this generalization is not mentioned in the passage.\n\n" +
                        "C is incorrect because while schools play a role, the passage focuses on habit formation not just in students but in the whole youth.\n\n" +
                        "D is incorrect because government programs can target all age groups, not just students.\n\n"));


        pages.add(new BookPage(
                "Governments could also do more to encourage their citizens to walk or ride a bicycle instead of taking the car, for instance by building more cycle lanes or increasing vehicle taxes. While some might argue that increased taxes are a negative way to solve the problem, Wilson (2014) highlights that it is no different from the high taxes imposed on cigarettes to reduce cigarette consumption.",
                R.drawable.obesity_5th, quizSets_5));


        List<QuizSet> quizSets_6= new ArrayList<>();

        quizSets_6.add(new QuizSet(
                "Why is it suggested that governments build more cycle lanes?",
                new String[]{"A. This will encourage people to drive less and exercise more.", "B. It can reduce car accidents and road congestion.", "C. It can increase revenue through cycling permits.", "D. It can make cycling competitions more popular."},
                1,
                "A. A is the correct answer because the passage suggests that building more cycle lanes would encourage people to drive less and exercise more. By making cycling a safer and more convenient option, governments can promote healthier lifestyles and reduce reliance on cars.",
                "A is the correct answer because the passage suggests that building more cycle lanes would encourage people to drive less and exercise more. By making cycling a safer and more convenient option, governments can promote healthier lifestyles and reduce reliance on cars.\n\n" +
                        "B is incorrect because while cycle lanes can improve road safety, the passage focuses on encouraging exercise.\n\n" +
                        "C is incorrect because the passage does not mention financial benefits of cycling permits.\n\n" +
                        "D is incorrect because the goal is to promote daily exercise, not sports competitions.\n\n"));

        quizSets_6.add(new QuizSet(
                "Why might some people disagree with raising taxes on riding vehicles?",
                new String[]{"A. It could affect low-income individuals.", "B. It might not significantly reduce car usage.", "C. It could harm businesses that rely on vehicle transportation.", "D. It would make bicycles more expensive."},
                1,
                "A. A is the correct answer because the passage acknowledges that some people might view increased vehicle taxes as a negative solution. One reason for this is that higher taxes could affect low-income individuals who rely on cars for transportation and may not have easy access to alternative options like cycling.",
                "A is the correct answer because the passage acknowledges that some people might view increased vehicle taxes as a negative solution. One reason for this is that higher taxes could affect low-income individuals who rely on cars for transportation and may not have easy access to alternative options like cycling.\n\n" +
                        "B is incorrect because the passage specifically highlights the impact on low-income individuals.\n\n" +
                        "C is incorrect because the passage does not mention business impacts.\n\n" +
                        "D is incorrect because the passage focuses on vehicle taxes, not on bicycle prices.\n\n"));





        pages.add(new BookPage(
                "In conclusion, obesity and poor fitness are a significant problem in modern life, leading to lower life expectancy. Individuals and governments can work together to tackle this problem and so improve diet and fitness." +
                        "Of the solutions suggested, those made by individuals themselves are likely to have more impact, though it is clear that a concerted effort with the government is essential for success. With obesity levels in industrialized and industrializing countries continuing to rise, it is essential that we take action now to deal with this problem.\n",
                R.drawable.obesity_6th, quizSets_6));


        List<QuizSet> quizSets_7= new ArrayList<>();

        quizSets_7.add(new QuizSet(
                "It was suggested that instead of driving one should learn to immerse him/herself in cycling or walking. What is one possible reason some people might not do this?\n",
                new String[]{"A. Walking and cycling are too easy and boring.", "B. Some places do not have safe sidewalks or bike lanes.", "C. Cars are banned in many cities.", "D. Walking and cycling are more expensive than driving."},
                2,
                "B is the correct answer because while walking and cycling are good alternatives to driving, some areas lack safe infrastructure such as sidewalks and bike lanes. This makes these activities dangerous for some people.",
                "B is the correct answer because while walking and cycling are good alternatives to driving, some areas lack safe infrastructure such as sidewalks and bike lanes. This makes these activities dangerous for some people.\n\n" +
                        "A is incorrect because this is subjective and not mentioned in the passage.\n\n" +
                        "C is incorrect because cars are not banned everywhere, and this is not the reason given in the passage.\n\n" +
                        "D is incorrect because generally, walking and cycling are more affordable.\n\n"));

        quizSets_7.add(new QuizSet(
                "A statement suggests that individuals should take action to improve their health. What might stop someone from doing this?\n",
                new String[]{"A. They do not have time or access to healthy food and exercise.", "B. Exercising too much makes people unhealthy.", "C. Everyone is naturally fit and does not need to make changes.", "D. They do not know how to use a car."},
                1,
                "A is the correct answer because even though improving health is encouraged, many people struggle due to time constraints or lack of access to healthy food and exercise facilities. Without these resources, making healthier choices becomes difficult.",
                "A is the correct answer because even though improving health is encouraged, many people struggle due to time constraints or lack of access to healthy food and exercise facilities. Without these resources, making healthier choices becomes difficult.\n\n" +
                        "B is incorrect because while over-exercising can be harmful, the passage focuses on barriers to exercising at all.\n\n" +
                        "C is incorrect because this is a false statement; obesity is a rising problem.\n\n" +
                        "D is incorrect because car use is unrelated to personal health choices.\n\n"));

        quizSets_7.add(new QuizSet(
                "A sentence tells that individuals should take responsibility for their health. What assumption does this make?\n",
                new String[]{"A. Everyone has equal access to healthy food and exercise opportunities.", "B. The government is unwilling to provide health programs.", "C. Obesity is not influenced by genetics.", "D. Modern technology can replace exercise."},
                1,
                "A is the correct answer because the statement that \"individuals should take responsibility for their health\" assumes that everyone has the same opportunities to do so. It implies that all people have equal access to healthy food and exercise options that makes it possible for them to make healthy choices.",
                "A is the correct answer because the statement that \"individuals should take responsibility for their health\" assumes that everyone has the same opportunities to do so. It implies that all people have equal access to healthy food and exercise options that makes it possible for them to make healthy choices.\n\n" +
                        "B is incorrect because the passage suggests that government intervention exists but may not be enough.\n\n" +
                        "C is incorrect because genetics play a role, but the passage does not discuss this factor.\n\n" +
                        "D is incorrect because the passage promotes physical activity, not technological solutions.\n\n"));

        quizSets_7.add(new QuizSet(
                "The narrative talks about processed foods being unhealthy. Why do people still eat them?\n",
                new String[]{"A. They have no taste compared to fresh foods.", "B. Restaurants do not serve processed foods.", "C. Doctors recommend processed foods for a balanced diet.", "D. They are usually cheaper and more convenient."},
                4,
                "D is the correct answer because processed foods are often cheaper and more convenient than fresh and healthy foods. Many people choose them due to busy schedules, limited budgets, or ease of preparation.",
                "D is the correct answer because processed foods are often cheaper and more convenient than fresh and healthy foods. Many people choose them due to busy schedules, limited budgets, or ease of preparation.\n\n" +
                        "A is incorrect because processed foods are often designed to be flavorful.\n\n" +
                        "B is incorrect because many restaurants serve processed foods.\n\n" +
                        "C is incorrect because doctors generally advise limiting processed foods.\n\n"));

        quizSets_7.add(new QuizSet(
                "What could be a limitation of relying on education as a solution to obesity?\n",
                new String[]{"A. Schools do not have enough time to teach health education.", "B. Knowledge alone does not always lead to behavior change.", "C. Only doctors should be responsible for educating people on diet and fitness.", "D. Education does not affect childhood obesity rates."},
                2,
                "B is the correct answer because while education is important, simply knowing about healthy habits does not always lead to actual behavior change. It should also be put into practice.",
                "B is the correct answer because while education is important, simply knowing about healthy habits does not always lead to actual behavior change. It should also be put into practice.\n\n" +
                        "A is incorrect because while time constraints exist, the main issue is that knowledge does not guarantee action.\n\n" +
                        "C is incorrect because education is not limited to doctors.\n\n" +
                        "D is incorrect because while education does not really affect obesity rates, the challenge is transforming knowledge into action.\n\n"));
        pages.add(new BookPage(
                "End of Story",
                0, quizSets_7));

        return pages;
    }


    private List<BookPage> loadMyFatherGoesToCourtPages()
    {
        List<BookPage> pages = new ArrayList<>();
        pages.add(new BookPage(
                "There was a family that lived in a small town on the Island of Luzon. Their father’s farm had been destroyed in 1898 by one of the Philippine floods. They had a very rich neighbor with two children that suddenly came out of the house. Their rich neighbor also had a tall house, and his children could look through the window of their house and watch them play, sleep, or eat when they had food to eat.\n\n" ,
                R.drawable.father_0, null));
        pages.add(new BookPage(
                "The rich man’s servant usually cooked special foods, and the children smelled the yummy aroma of the food. They always stayed by the window to smell the aroma of the food. Then, days passed, and the rich man’s children got thin and anemic while the happy children became robust and full of life.\n\n",
                R.drawable.father_2, null));
        List<QuizSet> quizSets_story = new ArrayList<>();
        quizSets_story.add(new QuizSet(
                "Where did the family live?",
                new String[]{
                        "A. They live in Manila.",
                        "B. They live in a small town on the Island of Luzon.",
                        "C. They live in Cebu.",
                        "D. They live in Mindanao."
                },
                2,
                "B is the correct answer because the story clearly says they lived in a small town in Luzon.",
                "A is incorrect because Manila is a big city, but the story says they lived in a small town.\n\n" +
                        "B is the correct answer because the story clearly says they lived in a small town in Luzon.\n\n" +
                        "C is incorrect because Cebu is a different island in the Philippines, not where they lived.\n\n" +
                        "D is incorrect because Mindanao is another island, but the story does not mention it.\n"
        ));

        quizSets_story.add(new QuizSet(
                "Why did the rich neighbor’s children watch the poor family?",
                new String[]{
                        "A. They were curious about how they lived.",
                        "B. They wanted to join them in playing.",
                        "C. They wanted to make fun of them.",
                        "D. They were jealous of their happiness."
                },
                4,
                "D is correct because even though the poor family was not rich, they were happy. The rich children, on the other hand, became sick and pale.",
                "A is incorrect because they might have been curious, but the main reason is they saw how happy the poor family was.\n\n" +
                        "B is incorrect because the story does not say they wanted to play together.\n\n" +
                        "C is incorrect because there is no sign that the rich children were teasing the poor children.\n\n" +
                        "D is correct because even though the poor family was not rich, they were happy. The rich children, on the other hand, became sick and pale.\n"
        ));

        quizSets_story.add(new QuizSet(
                "What does the contrast between the poor family’s happiness and the rich family’s illness suggest?",
                new String[]{
                        "A. Money does not always bring happiness.",
                        "B. Rich people are always sick.",
                        "C. The poor family used magic to stay strong.",
                        "D. Happiness depends on how much food one eats."
                },
                1,
                "A is correct because the rich family had money, but they were not happy. The poor family had little but still enjoyed life.",
                "A is correct because the rich family had money, but they were not happy. The poor family had little but still enjoyed life.\n\n" +
                        "B is incorrect because the story only talks about one rich family.\n\n" +
                        "C is incorrect because there is no magic in the story.\n\n" +
                        "D is incorrect because the poor family didn’t have much food but was still happy.\n"
        ));

        pages.add(new BookPage(
                "One day, the rich neighbor looked at them one by one—my sisters, my brothers—and then he banged down the window and immediately shut all the windows. From that day, they never saw the children again. The windows of the neighbor’s house were always closed. But still, they could smell the aroma of the food the servants were cooking.\n\n",

                R.drawable.father_3, quizSets_story));

        pages.add(new BookPage(
                "One morning, a policeman from the presidencia came to their house with a sealed paper. The rich man had filed a complaint against them. His father took him to the town clerk and asked what the complaint was about. Then the man claimed that they had been stealing the spirit of the rich man’s wealth and food.",
                R.drawable.father_4, null));

        List<QuizSet> quizSets_story_2 = new ArrayList<>();
        quizSets_story_2.add(new QuizSet(
                "What did the rich man do when he saw the children?",
                new String[]{
                        "A. He gave them food.",
                        "B. He closed all his windows.",
                        "C. He invited them inside.",
                        "D. He told his servants to stop cooking."
                },
                2,
                "B is correct because he shut the windows so they couldn’t smell his food anymore.",
                "A is incorrect because he did not share his food with them.\n\n" +
                        "B is correct because he shut the windows so they couldn’t smell his food anymore.\n\n" +
                        "C is incorrect because he did not welcome them at all.\n\n" +
                        "D is incorrect because he never stopped cooking, just tried to hide it.\n"
        ));

        quizSets_story_2.add(new QuizSet(
                "Why did the rich man file a complaint against the poor family?",
                new String[]{
                        "A. They were making too much noise.",
                        "B. They were stealing his money.",
                        "C. He believed they were stealing the spirit of his food.",
                        "D. They were trespassing on his property."
                },
                3,
                "C is correct because he thought that because they were smelling his food, they were \"taking\" its spirit.",
                "A is incorrect because the story never mentions noise as a problem.\n\n" +
                        "B is incorrect because the poor family never took any money.\n\n" +
                        "C is correct because he thought that because they were smelling his food, they were \"taking\" its spirit.\n\n" +
                        "D is incorrect because the poor family never physically entered his home.\n"
        ));

        quizSets_story_2.add(new QuizSet(
                "Do you think the rich man’s complaint was reasonable? Why or why not?",
                new String[]{
                        "A. Yes, because the poor family was taking something from him.",
                        "B. No, because smelling food does not mean stealing it.",
                        "C. Yes, because his children were getting sick.",
                        "D. No, because the rich man could have simply shared his food."
                },
                2,
                "B is correct because you can’t own a smell, so the poor family wasn’t doing anything wrong.",
                "A is incorrect because they didn’t take anything. Smelling something isn’t stealing.\n\n" +
                        "B is correct because you can’t own a smell, so the poor family wasn’t doing anything wrong.\n\n" +
                        "C is incorrect because their sickness was not caused by the poor family.\n\n" +
                        "D is incorrect because while sharing would have been kind, the main reason the complaint was unreasonable is that smelling is not stealing.\n"
        ));

        pages.add(new BookPage(
                "The day came when the poor family needed to appear in court. They were the first to arrive, and then his father sat on a chair in the center of the courtroom while his mother occupied the chair by the door. The children sat on a long bench by the wall. The rich man arrived. He had grown old, and his face was scarred with deep lines. With him was his young lawyer. Spectators came in and almost filled the chairs. The judge entered the room and sat on a high chair. They stood up in a hurry and then sat down again.",
                R.drawable.father_5, quizSets_story_2));
        List<QuizSet> quizSets_story_3 = new ArrayList<>();
        quizSets_story_3.add(new QuizSet(
                "Who arrived in court first?",
                new String[]{
                        "A. The rich man.",
                        "B. The poor family.",
                        "C. The judge.",
                        "D. The spectators."
                },
                2,
                "B is correct because they were the first to enter the courtroom.",
                "A is incorrect because the story states that the poor family arrived first.\n\n" +
                        "B is correct because they were the first to enter the courtroom.\n\n" +
                        "C is incorrect because the judge came after the spectators.\n\n" +
                        "D is incorrect because spectators came in after the family and before the judge.\n"
        ));

        quizSets_story_3.add(new QuizSet(
                "Why did the rich man bring a lawyer?",
                new String[]{
                        "A. He was unsure if he was right.",
                        "B. He wanted to win the case.",
                        "C. He was forced to do so.",
                        "D. He did not know how to speak for himself."
                },
                2,
                "B is correct because he hired a lawyer to make sure he won.",
                "A is incorrect because he thought he was right.\n\n" +
                        "B is correct because he hired a lawyer to make sure he won.\n\n" +
                        "C is incorrect because no one forced him to get a lawyer.\n\n" +
                        "D is incorrect because he spoke during the trial.\n"
        ));

        quizSets_story_3.add(new QuizSet(
                "What does the presence of spectators in the courtroom suggest?",
                new String[]{
                        "A. The case was seen as unusual or humorous.",
                        "B. The poor family was in serious legal trouble.",
                        "C. The judge needed witnesses.",
                        "D. The rich man wanted public support."
                },
                1,
                "A is correct because the idea of someone \"stealing a smell\" is strange, so people wanted to watch.",
                "A is correct because the idea of someone \"stealing a smell\" is strange, so people wanted to watch.\n\n" +
                        "B is incorrect because the poor family were not actually in big trouble.\n\n" +
                        "C is incorrect because the spectators were not witnesses.\n\n" +
                        "D is incorrect because there is no mention that he invited them.\n"
        ));

        pages.add(new BookPage(
                "The judge asked if the father had a lawyer with him, and the father replied that he did not need one. The lawyer of the rich man started with a question. The father agreed that they inhaled the aroma of the food. He also agreed that his children grew strong and healthy while the rich man’s children grew thin and pale.\n",
                R.drawable.father_6, quizSets_story_3));

        pages.add(new BookPage(
                "He asked if he could see the complainant’s children. Then, the children arrived shyly in the courtroom, looking thin and pale. The father asked the judge if he could examine the complainant, and the judge let him. He asked questions to the complainant, who agreed to all of it.",
                R.drawable.father_7, null));
        pages.add(new BookPage(
                "The father walked across the room with the judge’s permission. In his hand was a hat full of coins. The sweet tinkle of the coins carried beautifully in the courtroom. The spectators looked at it with wonder. The father asked the complainant if he heard the spirit of the money. The complainant answered yes. The father then said that they were paid.\n",
                R.drawable.father_8, null));

        List<QuizSet> quizSets_story_4 = new ArrayList<>();
        quizSets_story_4.add(new QuizSet(
                "What did the father carry in his hand?",
                new String[]{
                        "A. A piece of bread.",
                        "B. A bag of rice.",
                        "C. A hat full of coins.",
                        "D. A legal document."
                },
                3,
                "C is correct because the story clearly states that the father carried a hat filled with coins and used them to make a sound.",
                "A is incorrect because there is no mention of bread in the scene. The father used coins for his demonstration, not food.\n\n" +
                        "B is incorrect because the father did not bring rice; he brought coins to prove his point.\n\n" +
                        "C is correct because the story clearly states that the father carried a hat filled with coins and used them to make a sound.\n\n" +
                        "D is incorrect because the father did not have any legal papers; he just used coins to make his argument.\n"
        ));

        quizSets_story_4.add(new QuizSet(
                "What was the father trying to prove with the sound of the coins?",
                new String[]{
                        "A. That money is more important than food.",
                        "B. That sound can be considered payment, just as smell was considered theft.",
                        "C. That he was richer than the complainant.",
                        "D. That his family was not guilty."
                },
                2,
                "B is correct because the rich man claimed that the poor family “stole” the smell of his food, so the father used sound as a joke to show how silly that was.",
                "A is incorrect because the father was not trying to say money was more important than food. He was making a point about fairness.\n\n" +
                        "B is correct because the rich man claimed that the poor family “stole” the smell of his food, so the father used sound as a joke to show how silly that was.\n\n" +
                        "C is incorrect because the father was not trying to prove he was rich.\n\n" +
                        "D is incorrect because he was proving that the accusation was ridiculous, but he was not directly defending their innocence.\n"
        ));

        quizSets_story_4.add(new QuizSet(
                "When the judge allowed the poor father to demonstrate, what does this suggest about the judge?",
                new String[]{
                        "A. The judge was fair and open-minded.",
                        "B. The judge had already made up his mind.",
                        "C. The judge did not care about the case.",
                        "D. The judge did not understand the complaint."
                },
                1,
                "A is correct because the judge allowed both sides to speak and even let the father perform his demonstration.",
                "A is correct because the judge allowed both sides to speak and even let the father perform his demonstration.\n\n" +
                        "B is incorrect because if the judge had already decided, he would not have listened to the poor father’s point.\n\n" +
                        "C is incorrect because he listened carefully and made a fair decision.\n\n" +
                        "D is incorrect because he understood the case but saw that it was silly.\n"
        ));

        pages.add(new BookPage(
                "The rich man tried to talk, but then he fell to the floor without a sound, and his lawyer rushed to his aid. The judge pounded his gavel and said that the case was dismissed. The father asked the judge if he wanted to hear his family laugh. The judge agreed to hear it, and then they started laughing their hearts out, starting with his sister. The rest followed, but the judge was the loudest of them all.\n",
                R.drawable.father_9, quizSets_story_4));
        List<QuizSet> quizSets_story_5 = new ArrayList<>();
        quizSets_story_5.add(new QuizSet(
                "What happened to the rich man at the end of the trial?",
                new String[]{
                        "A. He won the case.",
                        "B. He fainted.",
                        "C. He argued with the judge.",
                        "D. He gave money to the poor family."
                },
                2,
                "B is correct because the story says he fell to the floor, showing he was overwhelmed or embarrassed.",
                "A is incorrect because he did not win; the case was dismissed.\n\n" +
                        "B is correct because the story says he fell to the floor, showing he was overwhelmed or embarrassed.\n\n" +
                        "C is incorrect because he did not fight back; he was too shocked.\n\n" +
                        "D is incorrect because he never gave them anything.\n"
        ));

        quizSets_story_5.add(new QuizSet(
                "Why did the judge dismiss the case?",
                new String[]{
                        "A. The father had a good lawyer.",
                        "B. The poor family was guilty.",
                        "C. The complaint was unreasonable.",
                        "D. The rich man was too weak to continue."
                },
                3,
                "C is correct because the whole idea of stealing a smell made no sense.",
                "A is incorrect because the father didn’t even have a lawyer.\n\n" +
                        "B is incorrect because they were not guilty because smelling is not stealing.\n\n" +
                        "C is correct because the whole idea of stealing a smell made no sense.\n\n" +
                        "D is incorrect because he fainted, but that’s not why the judge dismissed the case.\n"
        ));

        quizSets_story_5.add(new QuizSet(
                "Why do you think the judge joined in the laughter at the end?",
                new String[]{
                        "A. He found the case ridiculous.",
                        "B. He wanted to mock the rich man.",
                        "C. He believed the poor father had won fairly.",
                        "D. He was being paid by the poor family."
                },
                1,
                "A is correct because the idea of “stealing a smell” was so silly that even the judge laughed.",
                "A is correct because the idea of “stealing a smell” was so silly that even the judge laughed.\n\n" +
                        "B is incorrect because he was not trying to be mean to the rich man, just laughing at the situation.\n\n" +
                        "C is incorrect because the father didn’t \"win\" in a normal way; the case was just dismissed.\n\n" +
                        "D is incorrect because the judge was not bribed. He simply found the case funny.\n"
        ));

        pages.add(new BookPage(
                "End of the Story",
               0, quizSets_story_5));

        return pages;
    }
    private List<BookPage> loadDeforestationPages() {
        List<BookPage> pages = new ArrayList<>();

        pages.add(new BookPage(
                "Deforestation is a serious problem affecting many countries worldwide; the Philippines is no exception. The country is home to a vast array of unique flora and fauna, but deforestation has put many of these species at risk of extinction. Over the years, the Philippine government has implemented various programs and initiatives to combat deforestation and promote reforestation, but the battle is still ongoing. In this article, we will explore the progress made by the Philippines in the fight against deforestation, the challenges it faces, and what more needs to be done.\n",
                R.drawable.phili_1st, null));

        List<QuizSet> quizSets_deforestation_1 = new ArrayList<>();

        quizSets_deforestation_1.add(new QuizSet(
                "What is the main topic of the article?",
                new String[]{
                        "A. The unique flora and fauna of the Philippines.",
                        "B. The various programs implemented by the Philippine government.",
                        "C. The Philippines' ongoing battle against deforestation.",
                        "D. The risk of extinction faced by many species in the Philippines."
                },
                3,
                "C is correct because the article focuses on the Philippines' fight against deforestation, including progress, challenges, and what more can be done.",
                "A is incorrect because the article only mentions flora and fauna in relation to the impact of deforestation, not as the main topic.\n\n" +
                        "B is incorrect because the programs are just part of the overall battle against deforestation, not the main point.\n\n" +
                        "C is correct because the article focuses on the Philippines' fight against deforestation, including progress, challenges, and what more can be done.\n\n" +
                        "D is incorrect because while the article mentions the risk of extinction, it is not the primary focus.\n"
        ));

        quizSets_deforestation_1.add(new QuizSet(
                "What is a consequence of deforestation in the Philippines?",
                new String[]{
                        "A. Many species are at risk of extinction.",
                        "B. An increase in the number of unique species.",
                        "C. The implementation of successful reforestation programs.",
                        "D. The Philippine government has won the battle against deforestation."
                },
                1,
                "A is correct because deforestation destroys habitats, putting many species at risk of extinction.",
                "A is correct because deforestation destroys habitats, putting many species at risk of extinction.\n\n" +
                        "B is incorrect because deforestation reduces the number of unique species, not increases it.\n\n" +
                        "C is incorrect because reforestation programs are a response to deforestation, not a consequence.\n\n" +
                        "D is incorrect because the article states that the battle against deforestation is ongoing, not won.\n"
        ));

        pages.add(new BookPage(
                "Progress made by the Philippines\n" +
                        "\n" +
                        "The Philippines made significant progress in recent years in its efforts to combat deforestation. The government implemented various programs and policies to promote reforestation, sustainable forest management, and biodiversity conservation.\n" +
                        "One such program is the National Greening Program, which aims to plant 1.5 billion trees across 1.5 million hectares of land by 2028. The program has been successful in planting millions of trees across the country, and it has also created thousands of jobs in the forestry sector.\n" +
                        "In addition to the National Greening Program, the Philippines enacted various laws and policies to protect its forests. One such law is the Forest Management Bureau's Community-Based Forest Management Program, which aims to involve local communities in forest management and conservation efforts. The program successfully reduced deforestation rates in some areas and promoted sustainable forest management practices.\n",
                R.drawable.phili_2nd, quizSets_deforestation_1));


        List<QuizSet> quizSets_deforestation_2 = new ArrayList<>();

        quizSets_deforestation_2.add(new QuizSet(
                "What is the main goal of the National Greening Program?",
                new String[]{
                        "A. To create jobs in the forestry sector.",
                        "B. To plant 1.5 billion trees across 1.5 million hectares of land by 2028.",
                        "C. To enact new laws protecting forests.",
                        "D. To involve local communities in forest management."
                },
                2,
                "B is correct because the National Greening Program aims to plant 1.5 billion trees across 1.5 million hectares of land by 2028.",
                "A is incorrect because while the program creates jobs, it is not the main goal.\n\n" +
                        "B is correct because the National Greening Program aims to plant 1.5 billion trees across 1.5 million hectares of land by 2028.\n\n" +
                        "C is incorrect because the program focuses on planting trees, not enacting new laws.\n\n" +
                        "D is wrong as local community involvement is not the main goal of this program.\n"
        ));

        quizSets_deforestation_2.add(new QuizSet(
                "What is the purpose of the Community-Based Forest Management Program?",
                new String[]{
                        "A. To solely focus on planting trees.",
                        "B. To enforce stricter penalties for illegal logging.",
                        "C. To replace all existing forestry laws.",
                        "D. To involve local communities in forest management and conservation."
                },
                4,
                "D is correct because the Community-Based Forest Management Program involves local communities in forest management and conservation.",
                "A is incorrect because the program goes beyond just planting trees.\n\n" +
                        "B is incorrect because while the program may help enforce penalties, it is not its primary purpose.\n\n" +
                        "C is incorrect because the program does not replace existing forestry laws.\n\n" +
                        "D is correct because the Community-Based Forest Management Program involves local communities in forest management and conservation.\n"
        ));

        quizSets_deforestation_2.add(new QuizSet(
                "What is one positive effect attributed to the Community-Based Forest Management Program?",
                new String[]{
                        "A. It has increased deforestation rates in some areas.",
                        "B. It has promoted unsustainable forest management practices.",
                        "C. It has successfully reduced deforestation rates in some areas.",
                        "D. It has had no noticeable impact on deforestation."
                },
                3,
                "C is correct because the program has successfully reduced deforestation rates in some areas.",
                "A is incorrect because the program aims to reduce deforestation, not increase it.\n\n" +
                        "B is incorrect because the program promotes sustainable forest management practices.\n\n" +
                        "C is correct because the program has successfully reduced deforestation rates in some areas.\n\n" +
                        "D is incorrect because the article states that the program has had a positive impact.\n"
        ));

        pages.add(new BookPage(
                "Despite the progress made by the Philippines in its battle against deforestation, many challenges remain. One of the biggest challenges is illegal logging, which is a major problem in the country. Illegal logging contributes to deforestation and leads to the loss of biodiversity, soil erosion, and water pollution.\n\n" +
                        "Another challenge the Philippines faces is converting forests into agricultural land. With a rapidly growing population and a need to feed its citizens, the Philippines has seen increased forest conversion for agriculture. Unfortunately, this conversion often leads to unsustainable farming practices, such as slash-and-burn agriculture, further contributing to deforestation.\n",
                R.drawable.phili_3rd, quizSets_deforestation_2));

        List<QuizSet> quizSets_deforestation_3 = new ArrayList<>();

        quizSets_deforestation_3.add(new QuizSet(
                "Based on the text, what can you conclude about the impact of illegal logging on the Philippine environment?",
                new String[]{
                        "A. It has a positive effect on biodiversity.",
                        "B. It has no significant impact on the environment.",
                        "C. It causes environmental damage, including biodiversity loss.",
                        "D. It improves water quality and reduces soil erosion."
                },
                3,
                "C is correct as illegal logging causes environmental damage, including biodiversity loss as mentioned in the text.",
                "A is incorrect because illegal logging has a negative effect on biodiversity.\n\n" +
                        "B is incorrect because illegal logging has a significant impact on the environment.\n\n" +
                        "C is correct as illegal logging causes environmental damage, including biodiversity loss as mentioned in the text.\n\n" +
                        "D is incorrect because illegal logging degrades water quality and increases soil erosion.\n"
        ));

        quizSets_deforestation_3.add(new QuizSet(
                "Why is converting forests into farmland a problem in the Philippines?",
                new String[]{
                        "A. It creates more jobs for farmers.",
                        "B. It can lead to more deforestation and hurt the environment.",
                        "C. It helps the country grow more food.",
                        "D. It improves the quality of the soil."
                },
                2,
                "B is correct because converting forests into farmland often leads to more deforestation and environmental harm.",
                "A is incorrect because while it creates jobs, it is not the main reason of the problem.\n\n" +
                        "B is correct because converting forests into farmland often leads to more deforestation and environmental harm.\n\n" +
                        "C is incorrect because while it helps grow food, the environmental cost is too high.\n\n" +
                        "D is incorrect because converting forests often degrades soil quality.\n"
        ));

        quizSets_deforestation_3.add(new QuizSet(
                "What will likely happen to Philippine forests if illegal logging continues?",
                new String[]{
                        "A. The forests will experience significant expansion.",
                        "B. The forests will maintain their current size.",
                        "C. The forests will undergo a reduction in size.",
                        "D. The forests will remain unaffected."
                },
                3,
                "C is correct because illegal logging will continue to reduce the size of Philippine forests.",
                "A is incorrect because illegal logging leads to forest loss, not expansion.\n\n" +
                        "B is incorrect because illegal logging has a negative impact on forest size.\n\n" +
                        "C is correct because illegal logging will continue to reduce the size of Philippine forests.\n\n" +
                        "D is incorrect because illegal logging directly affects forests.\n"
        ));

        pages.add(new BookPage(
                "While the Philippines has made progress in its battle against deforestation, more must be done to protect the country's forests and the biodiversity they support. The government needs to enforce stricter laws and penalties against illegal logging and forest conversion. The country also needs to promote sustainable land-use practices and increase the involvement of local communities in forest management and conservation efforts.\n\n" +
                        "In addition, the Philippines needs to address the root causes of deforestation, such as poverty and a lack of alternative livelihoods for local communities. Providing alternative livelihood options, such as eco-tourism, agroforestry, and sustainable forestry, can help reduce the pressure on forests and promote sustainable land-use practices.\n",
                R.drawable.phili_4th, quizSets_deforestation_3));
        List<QuizSet> quizSets_deforestation_4 = new ArrayList<>();

        quizSets_deforestation_4.add(new QuizSet(
                "Why does the passage emphasize stricter laws and penalties against illegal logging?",
                new String[]{
                        "A. To increase government revenue.",
                        "B. To discourage illegal activities and protect forests.",
                        "C. To ensure that only certified timber reaches the market.",
                        "D. To make the logging industry more competitive."
                },
                2,
                "B is correct because stricter laws and penalties are needed to stop illegal activities and protect forests.",
                "A is incorrect because the focus is on protecting forests, not increasing revenue.\n\n" +
                        "B is correct because stricter laws and penalties are needed to stop illegal activities and protect forests.\n\n" +
                        "C is incorrect because while it may be a benefit, it is not the primary reason for stricter laws.\n\n" +
                        "D is incorrect because the goal is to discourage illegal activities, not make the industry more competitive.\n"
        ));

        quizSets_deforestation_4.add(new QuizSet(
                "Why are alternative livelihoods mentioned as a solution?",
                new String[]{
                        "A. To provide additional income for the government.",
                        "B. To create more jobs in the logging industry.",
                        "C. To make forests more accessible to communities.",
                        "D. To reduce reliance on forest resources for income."
                },
                4,
                "D is correct because alternative livelihoods reduce people's dependence on forest resources for income.",
                "A is incorrect because the focus is on helping communities, not the government.\n\n" +
                        "B is incorrect because alternative livelihoods aim to move people away from the logging industry.\n\n" +
                        "C is incorrect because the goal is to reduce reliance on forests, not make them more accessible.\n\n" +
                        "D is correct because alternative livelihoods reduce people's dependence on forest resources for income.\n"
        ));

        quizSets_deforestation_4.add(new QuizSet(
                "What can be concluded about the current state of forest conservation in the Philippines?",
                new String[]{
                        "A. The problem is completely solved.",
                        "B. Significant progress has been made, but further action is required.",
                        "C. No progress has been made.",
                        "D. The situation is hopeless."
                },
                2,
                "B is correct because the article states that progress has been made, but more action is needed.",
                "A is incorrect because the article acknowledges that challenges remain.\n\n" +
                        "B is correct because the article states that progress has been made, but more action is needed.\n\n" +
                        "C is incorrect because the article states that progress has been made.\n\n" +
                        "D is incorrect because the article presents a hopeful outlook, not a hopeless one.\n"
        ));

        pages.add(new BookPage(
                "The Philippines' battle against deforestation is ongoing, but progress has been made. The country implemented various programs and policies to promote reforestation, sustainable forest management, and biodiversity conservation. However, challenges remain, such as illegal logging and agricultural forest conversion.\n\n" +
                        "To protect the country's forests and the biodiversity they support, the government must enforce stricter laws and penalties against these practices and promote sustainable land-use practices. This is what Dutch Green Business (DGB) Group aims to do, promote sustainable practices and reforest the world at scale.\n",
                R.drawable.phili_5th, quizSets_deforestation_4));
        List<QuizSet> quizSets_deforestation_5 = new ArrayList<>();

        quizSets_deforestation_5.add(new QuizSet(
                "The article states that the Philippines has made \"significant progress\" in combating deforestation. What additional information would make this claim more convincing?",
                new String[]{
                        "A. A statement from a government official.",
                        "B. A quote from an environmental activist.",
                        "C. Specific data showing a decrease in deforestation rates or an increase in forest cover over a specific period.",
                        "D. A picture of a newly planted forest."
                },
                3,
                "C is correct because specific data would provide objective evidence of the progress.",
                "A is incorrect because a statement from a government official is not enough evidence.\n\n" +
                        "B is incorrect because a quote from an activist is subjective and not data-driven.\n\n" +
                        "C is correct because specific data would provide objective evidence of the progress.\n\n" +
                        "D is incorrect because a picture is not sufficient evidence of overall progress.\n"
        ));

        quizSets_deforestation_5.add(new QuizSet(
                "What is a key limitation of relying solely on reforestation to combat deforestation?",
                new String[]{
                        "A. Reforestation does not address the root causes of deforestation.",
                        "B. Reforestation is always successful.",
                        "C. Reforestation is inexpensive and easily implemented.",
                        "D. Reforestation is a rapid process."
                },
                1,
                "A is correct because reforestation does not address the underlying reasons for deforestation, such as poverty or demand for agricultural land.",
                "A is correct because reforestation does not address the underlying reasons for deforestation, such as poverty or demand for agricultural land.\n\n" +
                        "B is incorrect because reforestation is not always successful.\n\n" +
                        "C is incorrect because reforestation can be expensive and difficult to implement effectively.\n\n" +
                        "D is incorrect because reforestation is a long-term process, not rapid.\n"
        ));

        quizSets_deforestation_5.add(new QuizSet(
                "What is a primary benefit of sustainable agricultural practices?",
                new String[]{
                        "A. Reduced pressure on forests for agricultural expansion.",
                        "B. Improved soil health and reduced reliance on chemical inputs.",
                        "C. Increased biodiversity in agricultural areas.",
                        "D. All of the above are benefits."
                },
                4,
                "D is correct because sustainable agricultural practices have multiple benefits, including reduced pressure on forests, improved soil health, and increased biodiversity.",
                "A, B, and C are all benefits of sustainable agriculture, making D the most comprehensive answer.\n\n" +
                        "D is correct because sustainable agricultural practices have multiple benefits, including reduced pressure on forests, improved soil health, and increased biodiversity.\n"
        ));

        quizSets_deforestation_5.add(new QuizSet(
                "What is a significant positive outcome of DGB Group's global reforestation initiative?",
                new String[]{
                        "A. Increased company profits.",
                        "B. Substantial carbon dioxide absorption, aiding climate change mitigation.",
                        "C. Immediate economic gains for all involved communities.",
                        "D. Complete elimination of global deforestation."
                },
                2,
                "B is correct because DGB Group's initiative helps absorb carbon dioxide, aiding climate change mitigation.",
                "A is incorrect because the article does not mention increasing company profits as a primary outcome.\n\n" +
                        "B is correct because DGB Group's initiative helps absorb carbon dioxide, aiding climate change mitigation.\n\n" +
                        "C is incorrect because the article does not focus on immediate economic gains for communities.\n\n" +
                        "D is incorrect as it cannot completely eliminate global deforestation.\n"
        ));

        pages.add(new BookPage(
                "End of The Story",
                0, quizSets_deforestation_5));


        return pages;
    }
    @Override
    public void onBackPressed() {
        ConfirmationDialog.show(this, new ConfirmationDialog.ConfirmationListener() {
            @Override
            public void onConfirm() {
                finish(); // Only finish the activity when the user confirms
            }

            @Override
            public void onCancel() {
                // Do nothing; remain in the activity
            }
        });
    }

}
