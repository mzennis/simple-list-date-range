package com.pandaways.simplelist.data

import com.pandaways.simplelist.R
import com.pandaways.simplelist.model.PersonUiModel
import java.util.*

object PersonMock {

    fun personList(): List<PersonUiModel> = List(24) {
        PersonUiModel(
            imageRes = listOf(
                R.drawable.ava_female_1,
                R.drawable.ava_female_5,
                R.drawable.ava_male_2,
                R.drawable.ava_male_3,
                R.drawable.ava_male_4,
                R.drawable.ava_male_6
            ).random(),
            name = listOf(
                "Chelsea Cutler",
                "Madison Beer",
                "Jeremy Zucker",
                "Arash Buana",
                "Troye Sivan",
                "Lewis Capaldi",
                "Teddy Adhitya",
            ).random(),
            birthDate = generateRandomDate(),
            address = listOf(
                "Rancabali, Bandung, Jawa Barat",
                "Ungaran, Semarang, Jawa Tengah",
                "Grogol, Surakarta, Jawa Tengah",
                "Diwek, Jombang, Jawa Timur",
                "Glagah, Banyuwangi, Jawa Timur",
                "Kuta, Denpasar, Bali",
                "Kujuagung, Palembang, Sumatera Selatan"
            ).random(),
            sex = listOf(
                "Male",
                "Female",
            ).random()
        )
    }

    private fun generateRandomDate(): Date {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, (1990..2022).random())
            set(Calendar.DAY_OF_YEAR, (1..365).random())
        }.time
    }
}