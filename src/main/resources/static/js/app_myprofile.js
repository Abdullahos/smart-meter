//select the elements
var togglebutton;
var bigwrapper;
var burgermenu;

function declare()
{
    togglebutton = document.querySelector(".togglebutton");
    bigwrapper = document.querySelector(".bigwrapper");
    burgermenu = document.querySelector(".burgermenu");
}

const main = document.querySelector("main");

declare();

let dark = false;

function toggleAnimation()
{
    dark = !dark;
    let clone = bigwrapper.cloneNode(true);
    if(dark)
    {
        clone.classList.remove("light");
        clone.classList.add("dark");
    }else{
        clone.classList.remove("dark");
        clone.classList.add("light");
    }
    clone.classList.add("copy");
    main.appendChild(clone);

    document.body.classList.add("stopscrolling");

    clone.addEventListener("animationend", () => {
        document.body.classList.remove("stopscrolling");
        bigwrapper.remove();
        clone.classList.remove("copy");
        //reset variables
        declare();
        events();
    });
}
function events()
{
    togglebutton.addEventListener("click", toggleAnimation);
    burgermenu.addEventListener("click", () => {
        bigwrapper.classList.toggle("active");
    });
}
events();