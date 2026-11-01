package com.example.burtvesproj.data

import com.example.burtvesproj.R

object MockData {

    fun getAppsData(): List<AppItem> = listOf(
        AppItem(
            id = 1,
            title = "Telegram",
            subtitle = "Мессенджер",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Инструкция по работе с Telegram:\n\n1. Откройте приложение\n2. Введите номер телефона\n3. Подтвердите код из SMS\n4. Начните общение!",
            graphicStepImages = listOf(
                R.drawable.bookmark,
                R.drawable.home,
                R.drawable.search
            )

        ),
        AppItem(
            id = 2,
            title = "YouTube",
            subtitle = "Видео",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Инструкция по работе с YouTube:\n\n1. Откройте приложение\n2. Войдите в аккаунт Google\n3. Ищите видео через поиск\n4. Смотрите и подписывайтесь!"
        ),
        AppItem(
            id = 3,
            title = "Spotify",
            subtitle = "Музыка",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Инструкция по работе со Spotify:\n\n1. Установите приложение\n2. Зарегистрируйтесь\n3. Найдите любимые треки\n4. Создайте свой плейлист"
        )
    )

    fun getDevicesData(): List<AppItem> = listOf(
        AppItem(
            id = 1,
            title = "Принтер HP",
            subtitle = "Печать",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Инструкция по подключению принтера HP:\n\n1. Включите принтер\n2. Подключите к Wi-Fi\n3. Установите драйверы\n4. Нажмите 'Печать' в любом документе"
        ),
        AppItem(
            id = 2,
            title = "Роутер TP-Link",
            subtitle = "Сеть",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Инструкция по настройке роутера:\n\n1. Подключите кабель питания\n2. Соедините с компьютером\n3. Откройте 192.168.0.1\n4. Настройте интернет"
        ),
        AppItem(
            id = 3,
            title = "Наушники Sony",
            subtitle = "Bluetooth",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Инструкция по сопряжению наушников:\n\n1. Включите Bluetooth на телефоне\n2. Зажмите кнопку на наушниках\n3. Найдите устройство в списке\n4. Нажмите 'Подключиться'"
        )
    )
}