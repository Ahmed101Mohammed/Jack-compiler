# Jack-compiler
A compiler for jack language, designed by instructors in the [Nand2Tetris course](https://www.coursera.org/learn/nand2tetris2/home/welcome).

## Project description:
### What project do:
It is a compiler for a High-Level OO programming language called Jack, designed by instructors in [Nand2Tetris course](https://www.coursera.org/learn/nand2tetris2/home/welcome).
The compiler task  compiling your code from Jack OOPL to VM language.
VN is a procedural language, that was also designed by the instructors form the [Nand2Tetris course](https://www.coursera.org/learn/nand2tetris2/home/welcome).
### Project Technologies:
I build this compile with just Java.
### Main challenge I faced in this project:
I faced many challenges in this project, but the main one was building the parser. 
#### The main questions I faced are:
- What is the architecture of the parser?
- How to get benefits from the result of my Tokenizer?
- How to make the next step (Building code generator) after built the parser more easier?
#### My solution:
> After the tokenizer finishes its work, it gives a stream of objects, each of them presents information for a single token, And it makes it easier to start working with objects instead of starting with a stream of chars again from plain text.
> Also for generating code it will be hard to work just starting with a stream of objects without but them in some more complex object that can present some more advanced contexts.
> So I find the best solution for me, is to build this parser as an OOP project, that each command of Jack language can represent as an object, for example, class jack command instruction is an object, that contains multiple objects as properties of ClassJackCommand object, and these objects are all subroutines of the jack class object, and variable declarations. and each subroutine and variable declarations are also objects that contain other objects that can represent more specific commands and so on. 

#### What I learn from this challenge:
The solution that I chose answered my questions, but in the same time is make me recognize some big costs that my solution lead to, although I chose it in the end.
> The big cost was the memory, if you imagine that the compiler will build a real parsing tree in the memory, and your code is millions or billions of lines, this will lead to high memory and time costs, because the compiler will record each class and subroutines or statement as an object, to be more detectable, each word or symbol in your base code, will have an object in the memory represent it, so it's a big cost.
> So another solution that you can follow: is to build the compiler in a more procedural way, I haven't tried this solution before, but I can expect, it maybe more challenging, and it's the recommentded solution in [Nand2Tetris course](https://www.coursera.org/learn/nand2tetris2/home/welcome).

