# Тестовое для СПБ ТСБ
* REST-сервис для проведения операций со счетом (1 счет, 1 клиент, валюта RUR)
* 2 вида покупок - онлайн и в магазине
* за покупки в магазине автоматически начисляется кэшбек 10%
* за онлайн покупки до 20р начисляется 10%, 21-300: 17%, > 300р: 30%
* реализовать эндпйонты для оплаты покупок, получения начисленного кэшбека, количества средста на счете
* реализовать статусную модель
* реализовать паттерн Состояние

# Реализация
* для простоты проверки выбрана БД H2, чтобы не устанавливать доп. зависимости
* REST-сервис реализован на Spring WEB
* все действия со счетом проходят в рамках транзакций
* покупка и зачисление бонуса происходят так же в рамках 1 транзакции
* статусная модель реализована для счета - он может быть активный или заблокированный
* реализация паттерна Состояние реализована для статусов счета - активный или заблокированный
* для удаленного счета нет доступных операций
* для активных счетов доступны любые операции

# Покупка и зачисление бонусов
* счет состоит из 2 частей - начисленные бонусы и обычный рублевый баланс
* счета разделены, тк в эндпойнтах хотим получать отдельно баланс только по начиселнным бонусам
* при попытке оплаты средства сначала спишутся с обычного баланса
* если средств на обычном балансе не хватает, пытаемся списать остаток цены с балансового счета
* если средств не хватает даже с учетом баланса, возвращаем пользователю ошибку о неудчной покупке и нехватке средств

# Тестирование
* main метод в приложении оставляем пустым, тк проверка тест кейсов проходит в тестах в папке test
* в папке test реализованы тесты для контроллера, создания и сохранения данных по счету, юнит-тесты для операций по счету и правильному отрабатыванию State-паттерна
* основной тест-кейс из ТЗ лежит в https://github.com/Dianana101/spb-br/blob/b6fb4991e0aa27cede8415de2346e078a1ee6731/src/test/java/com/example/spb_br/service/AccountServiceImplTest.java#L32

# БД
* подключение к БД H2 происходит автоматически со стартом приложения
* посмотреть содержимое БД можно через консоль БД в браузере http://localhost:8080/h2-console/ , название бд: spb_br_db, пользак: sa, вход по умолчанию без пароля