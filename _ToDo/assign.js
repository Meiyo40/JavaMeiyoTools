const {fromEvent, Observable, debounceTime, map} = rxjs;
const inputItem = document.getElementById('inputItem');
const btnAdd = document.getElementById('btn-additem');

fromEvent(btnAdd, 'click')
.subscribe(x => console.log("Item Added"));

const searchItem$ = new Observable((observer) => {
    inputItem.addEventListener("input", (e) => {
        observer.next(e);
    })
});

const searchItemPipe$ = searchItem$.pipe( 
    map(e => e.target.value),
    debounceTime(300)
)

searchItemPipe$.subscribe(v => {
    console.log(v);
})