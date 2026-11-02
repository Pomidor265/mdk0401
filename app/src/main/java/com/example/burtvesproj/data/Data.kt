package com.example.burtvesproj.data

import com.example.burtvesproj.R

object MockData {

    fun getAppsData(): List<AppItem> = listOf(
        AppItem(
            id = 1,
            title = "Не приходят уведомления",
            subtitle = "WhatsApp",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Перейдите в настройки вашего устройства, найдите раздел «Приложения», внутри этого раздела найдите WhatsApp, там будет плашка «Уведомления». Если оно выключено, включите",
            graphicStepImages = listOf(
                R.drawable.bookmark,
                R.drawable.home,
                R.drawable.search
            )

        ),
        AppItem(
            id = 2,
            title = "Код подтверждения не приходит при регистрации",
            subtitle = "WhatsApp",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Убедитесь, что номер введен верно. Попробуйте запросить звонок (кнопка «Позвонить мне»). Подождите несколько минут и попробуйте снова"
        ),
        AppItem(
            id = 3,
            title = "Создание резервной копии чатов ",
            subtitle = "WhatsApp",
            imageResId = R.drawable.ic_launcher_background,
            instructionText = "Откройте WhatsApp, нажмите три точки в правом верхнем углу и перейдите в настройки. Перейдите в «Чаты», опуститесь вниз и нажмите на «Резервная копия чатов», выберите аккаунт Google куда будет сохраняться копия чатов. Вы также можно выбрать частоту создания резервных копий."
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