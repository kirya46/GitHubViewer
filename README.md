Описани приложения.

1. Реализована авторизация через аккаунт GitHub. Поиск доступен только для авторизированого пользователя, а результаты предыдущего поиска доступны и неавторизированому пользователю.

2. Результаты выдаются "страницами" по 15 елементов, результаты ищутся по 2 страници (30 елементов)

3. Реализована пагинация для подгрузки следующих результатов, все так же по 2 страници (30 елементов).

4. По умолчанию репозитории сортируются по количеству звездю

5. После начала поиска - его  ( поиск ) можно остановить.

6. Названия репозиториев ограничены длиной 30 символов, а так же указан 
   параметр  android:ellipsize="end" в файлу res/activity_main_list_item.xml
   Этот же функционал можно и в другой способ реализовать.

   P.S. если я правильно понял этот пункт задания.

7. При нажатии на репозитории - открывается браузер Chrome с ссылкой на страницу репозитория.

8. Предыдущие результаты поиска можно посмотреть без интернета, после открытия страници репозитория в Chrome - этот репозиторий будет помечен как просмотреный (иконка глаз в елементе списка)

9. Предыдущие результаты поиска можно редактировать: удалять, перемещать.



P.S. Так как мое время было несколько ограничено, в проекте присутствуют лишь частичные тесты.

P.S. 2 Так же в проекте можно было реализовать Repository используя не просто Observable,а Android Architecture Componens, в частности LiveData + NetworkBoundResource для совмещения кешированых и новых данных из интернета.

ВАЖНО: для авторизированого пользователя - количество запросов к GitHub Api = 30 request/min, следовательно  подгрузка дополнительных результатов возможна лишь до того момента пока этот лимит не исчерпается, после чего GitHub Api будет возращать код 403 Forbidden.

ВАЖНО 2: для неавторизированого пользователя в ситуации когда видны предыдущие результаты поиска - подгрузка следующих результатов не работает, пока он не авторизируется.



![alt text](https://github.com/kirya46/GitHubViewer/blob/master/screenshots/Screenshot_1531306625.png)

![alt text](https://github.com/kirya46/GitHubViewer/blob/master/screenshots/Screenshot_1531306609.png)

![alt text](https://github.com/kirya46/GitHubViewer/blob/master/screenshots/Screenshot_1531306869.png)
