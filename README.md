# 20221103_歩数計(만보기, pedometer) 開発中止
- AndroidからのStepCounterAPIの機能的な限界で開発中止
- Android에서 StepCounter API의 기능적 한계로 개발 중지
- Development stopped due to functional limitations of StepCounterAPI from Android

# 20221104_家計簿(가계부, AccountBook)_Init
- Reference : [楽な家計簿(편한가계부)](https://play.google.com/store/apps/details?id=com.realbyteapps.moneymanagerfree&hl=ko&gl=US)

# 20221129_家計簿(가계부, AccountBook)_関連UIのサンプリング進行中
- 以前のコミット参考

# 221130_家計簿(가계부, AccountBook)_文書作成
- [Figma Link](https://www.figma.com/file/lbeiHZIfuF97k23i4r77rx/AccountBookUI?node-id=4%3A3&t=9val6cYOjs3XBr31-1)
- サンプリングプロジェクトもどんどん大きくなっていて、全般的な管理もできないし、以前のこととの間違えが増えている。全般的な定義、設計、流れを見てみ台と思う。
- 샘플링 프로젝트도 점점 커지고 있고, 전반적인 관리도 안 되고, 이전의 일과의 오류가 증가하고 있다.전반적인 정의, 설계, 흐름을 살펴보자.
- Sampling projects are getting bigger and bigger, and they can't be managed in a full-scale manner, and the number of mistakes they made is increasing.I think it's a good idea to look at the overall definition, design, and flow.
- 画面遷移、画面でのステータス、データ定義などを確認して開発にも為になるし、設計など見る目も広がると予想。
- 화면 전이, 화면에서의 상태, 데이터 정의등을 확인해 개발에도 도움이 되고, 설계등 보는 눈도 넓어질 것으로 예상.
- It is expected to be useful for development by checking the screen transition, status on the screen, data definition, etc., and expand the view of the design.

# 221130_家計簿(가계부, AccountBook)_文書作成中
- [Figma Link](https://www.figma.com/file/lbeiHZIfuF97k23i4r77rx/AccountBookUI?node-id=4%3A3&t=9val6cYOjs3XBr31-1)
- 楽な家計簿の全ての画面をグループ化し、流れを整理し、画面ごとのデータ定義を予想してみた。
- 편한 가계부의 모든 화면을 그룹화해 흐름을 정리하고 화면별 데이터 정의를 예상해봤다.
- We grouped all the screens in the comfortable household account book, organized the flow, and predicted the definition of data for each screen.

# 221208_家計簿(가계부, AccountBook)_レイアウトに基づく修正
- [Sample Project Link](https://github.com/doyou1/android-example-workspace/tree/master/MultiFragmentInSingleActivitySampling)
- Fragment内のSwipe Eventを通じてActivityのState Changeを反映し、Fragment Refreshする機能、そしてTabLayoutを通じてFragment replaceを進め、Swipe Eventも統一感のある動作を実現するのに大変だった。 様々な方式でサンプリングし、TABLayout、FrameLayout、FrameLayoutにOnTouchListenerを付ける方式を採用した。 他の簡単できれいな方法もあるだろうが、今のところ私が自らコード動作を理解して具現できる最善の方法だ。 人は危機の中で強くなる。
- Fragment내에서의 Swipe Event를 통해 Activity의 State Change 및 반영하고 Fragment Refresh하는 기능, 그리고 그 와중에 TabLayout을 통해 Fragment replace를 진행하며, Swipe Event 역시 통일감 있게 동작하는 Layout을 구현하는 데, 많이 힘들었다. 여러가지 방식으로 샘플링 해보며, TabLayout, FrameLayout, FrameLayout에 OnTouchListener를 다는 방식을 채택했다. 다른 쉽고, 깔끔한 방법도 있겠지만, 지금 내 선에선 내가 스스로 코드 동작을 이해하고 구현할 수 있는 최선의 방식이다. 사람은 위기 속에서 강해진다.
- The function of changing the state of activity and reflecting and refreshing the fragment through the Sweep Event within the Fragment, and in the meantime, the Fragment Replace through TabLayout, and the Sweep Event was also very difficult to implement a layout that operates in a unified manner. By sampling in various ways, we adopted the method of attaching the OnTouch Listener to TabLayout, FrameLayout, and FrameLayout. There are other easy and neat ways, but at this point, it's the best way for me to understand and implement code behavior on my own. A man becomes strong in a crisis.

# 221209~_家計簿(가계부, AccountBook)_Room適用
- [Sample Project Link](https://github.com/doyou1/android-example-workspace/tree/master/RoomDBSampling)
- [Image Save in Room Prject Link (falied)](https://github.com/doyou1/android-example-workspace/tree/master/RoomDBSampling)
- ついに家計簿の花、作成した履歴を読んで、書いて、修正できるようにRoom DBを適用してみた。 しかし、現在のDDLそのまま適用するには考慮すべき事項が多く、アセットとCategoryのグループIDなどは果敢に除去して進めた。 それにもかかわらず、CRUDを直ちに具現するのに困難があり、先にSamplingを進めた。 その過程でRoomと関連した細部知識を得ることができ、内部DBであるRoomだけでは具現が難しい部分もあった。 （imageはByte Arrayのサイズが大きすぎてInsertは可能だがSelectができない問題があり、一般DBでは簡単に適用できるオブジェクト参照部分もアンドロイド側でわざと塞いでおいたという事実も分かった。（詳細は[Link]（https://developer.android.com/training/data-storage/room/referencing-data)最下段チャプター参照）） まだ具現適用されていない部分はAsset、Categoryの追加および編集など多くの部分が残っている。 Image 関連処理はBackend サーバー実装を追加しなければならないようだ。 今年もお疲れ様。 来年もファイト！
- 드디어 가계부의 꽃, 작성한 이력들을 읽고, 쓰고, 수정할 수 있도록 Room DB를 적용해봤다. 하지만, 현재의 DDL 그대로 적용하기에는 고려해야 할 사항들이 많아서 Asset과 Category의 그룹 ID 등은 과감하게 제거하고 진행했다. 그럼에도 불구하고, CRUD를 바로 구현하는 데 어려움이 있어서 먼저 Sampling을 진행했다. 그 과정에서 Room과 관련한 세부 지식들을 얻을 수 있었고, 내부 DB인 Room만으로는 구현이 어려움 부분들도 있었다. (image는 ByteArray의 Size가 너무 커서 Insert은 가능하나 Select가 안되는 문제가 있었고, 일반 DB들에선 쉽게 적용 가능한 객체 참조 부분도 안드로이드 쪽에서 일부러 막아뒀다는 사실도 알 수 있었다.(자세한 내용은 [Link](https://developer.android.com/training/data-storage/room/referencing-data) 최하단 챕터 참조)). 아직 구현 적용이 안된 부분은 Asset, Category 추가 및 편집 등 많은 부분들이 남아있다. Image 관련 처리는 Backend 서버 구현을 추가해야 할 것 같다. 올해도 수고 많았다. 내년에도 화이팅
- Finally, I applied Room DB so that I can read, write, and modify the flowers of the diary and the history I wrote. However, there were many things to consider to apply as the current DDL, so the group IDs of Asset and Category were boldly removed and proceeded. Nevertheless, it was difficult to implement CRUD immediately, so sampling was conducted first. In the process, detailed knowledge related to Room could be obtained, and there were some parts that were difficult to implement only with Room, an internal DB. (Image shows that ByteArray's size was too large to allow Insert but not Select, and that Android intentionally blocked the easily applicable object reference parts in general DBs (see [Link] (see the bottom chapter at https://developer.android.com/training/data-storage/room/referencing-data) for more information). Many parts that have not yet been implemented remain, such as Asset, Category addition, and editing. Image-related processing seems to need to add a Backend server implementation. You did a great job this year. Fighting next year, too!

# 230103~_家計簿(가계부, AccountBook)_定期収入、分割払い関連ロジック
- [Sample Project Link](https://github.com/doyou1/android-example-workspace/tree/master/RegularIncomeAndInstallmentSampling)
- 家計簿なら定期収入と分割払い機能がなければならない。 これをHistoryに保存することは問題ではないが、以後時間が経ってHistoryが自動的に追加される機能が必要だ。 最初はAlarmManagerとBroadCast Receiverを通じて追加するように具現しようとしたが、機能的に正確な実行と複数実行が不可能な問題がありあきらめた。 次はアプリ起動時にRoomDBデータを読んで分析および処理する方式で進めてみた。 既存のカラムだけでは対応が難しい部分があり、isNewFlagを追加して最新データだけを対応できるようにした。 今後、分割払い関連ロジックも追加しなければならない。 今の考えでは分割払い回数を数えるカウントカラムが必要だと思われる。 現場でDBを見るとテーブルカラムが数百個ずつあるが、その理由が理解できた。
- 가계부라면 정기 수입와 할부 기능이 있어야한다. 이를 History에 저장하는 건 문제가 아니지만, 이후 시간이 지나 History가 자동으로 추가되는 기능이 필요하다. 최초에는 AlarmManager와 BroadCastReceiver를 통해 추가하도록 구현하려 했지만, 기능적으로 정확한 실행과 복수 실행이 불가능한 문제들이 있어 포기했다. 다음은 앱기동시 RoomDB 데이터를 읽어 분석 및 처리하는 방식으로 진행해봤다. 기존의 컬럼만으로는 대응이 어려운 부분이 있어 isNewFlag를 추가해 가장 최신 데이터만을 대응할 수 있도록 했다. 추후 할부 관련 로직도 추가해야 한다. 지금 생각으로는 할부 횟수를 세는 count 컬럼이 필요할 것으로 생각된다. 현장에서 DB를 보면 테이블 컬럼이 몇백개씩 되던데, 그 이유를 이해했다.
- AccountBook must have regular income and installment functions. It is not a problem to save it in History, but it requires a function that automatically adds History over time. Initially, it tried to implement it to be added via AlarmManager and BroadCastReceiver, but gave up due to problems that could not be functionally accurate and multiple execution. Next, we tried to read, analyze, and process RoomDB data when the app starts. Since it is difficult to respond only with existing columns, isNewFlag has been added so that only the latest data can be responded. Logic related to installments should also be added in the future. In my opinion, I think we need a count column to count the number of installments. If you look at the DB in the field, there are hundreds of table columns, and I understand why.

# 230105~_カジュアル賭けゲーム(캐주얼 내기 게임, CasualBettingGame)_はしごゲーム、ルーレットSampling
- [Sample Project Link](https://github.com/doyou1/android-example-workspace/tree/master/CustomDrawingSampling)
- 実際に発売してみたいアプリは、プロジェクトサイズが大きくないカジュアル賭けゲームなので、関連したサンプリングを進めてみた。 簡単に進行できると思ったが、思ったよりロジックが複雑な部分もあって時間がかかった。 はしごゲームはロジック的には時間をかけて解決したが、特に結果を示す時、ゆっくり埋めていく部分はまだ具現方法を見出せずにいる。 細かいことに色々足りない部分が多くて4月前に発売してみたいと思ったが、容易ではないと思った。
- 실제로 출시해보고 싶은 어플은 프로젝트 크기가 크지않은 캐주얼 내기 게임이라서 관련한 샘플링을 진행해봤다. 쉽게 진행할 수 있을 줄 알았는데, 생각보다 로직이 복잡한 부분들도 있어서 시간이 많이 걸렸다. 사다리 게임은 로직적으론 시간을 써서 해결했지만, 특히 결과를 보여줄 때, 천천히 채워나가는 부분은 아직 구현 방법을 찾지 못했다. 자잘하게 이런저런 부족한 부분들이 많아서 4월전에 출시해보고 싶다고 생각했지만, 쉽지 않겠다는 생각이 들었다.
- In fact, the application I want to release is a casual betting game with a small project size, so I conducted a related sampling. I thought it would be easy to proceed, but it took a lot of time because the logic was more complicated than I thought. The ladder game was solved logically by using time, but especially when showing the results, the slow filling part has yet to be realized. I wanted to release it before April because there were many shortcomings, but I thought it would not be easy.

# LiveData(勉強要)
- [references](https://kotlinworld.com/47)

# 20230531 - VocabularyNote
- feedback link : [here](https://docs.google.com/spreadsheets/d/1xiK0DtNJV101cxfbeTtoUxRMc3O5iUmQcQOeIptf8u4/edit?usp=sharing)