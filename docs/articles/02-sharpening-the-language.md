# Sharpening the Language: Killing "Float-Soup" Before I Wrote a Line of Java

Last week I said I was rewriting my market-valuation app from Python to the JVM because correctness matters. This week I want to show you the very first thing I did - and it wasn't code. It was a dictionary.

Here's why. I opened my own working app and realized I couldn't tell you what the word "valuation" meant. Not because I'd forgotten - because it meant **three different things in three different places**.

## One word, three meanings

Reading through the prototype, "value" and its cousins were everywhere, and they never agreed:

- `FairValue` - a column holding the model's predicted price for a historical month.
- `calculated_price` - the *same idea* (a predicted price), but for a current scenario, under a different name.
- `valuation` - a *ratio* of market price to predicted price.
- `StockValuation` - the whole result object bundling everything.
- `valued` / `diff` - the actual over/under verdict and its percentage gap.

So `valuation` pointed at both the result object *and* a ratio. "Fair value" straddled historical and current. The single most important concept in the entire application - what the thing actually computes - had no stable name. If I can't name it, I can't reason about it, and neither can anyone reading the code.

And underneath the naming mess sat what I started calling **float-soup**. Here's a real constructor:

```
Coefficients("S&P 500", intercept, treasury, earnings, dividend, date)
```

Five bare numbers in a row. Swap `earnings` and `dividend` and nothing complains; you just get a quietly wrong answer about money. Another one:

```
StockEarningsModel(earnings, "Trailing earnings", calculated_price, valued, diff)
```

None of these values carries any meaning. They're floats and strings in a positional line, and their correctness rests entirely on "trust me, the order is right."

## The decision: build the language first

The fix isn't a clever type or a framework. It's a **ubiquitous language** - the Domain-Driven Design idea that the team (even a team of one) agrees on precise words for domain concepts, and then the code speaks those exact words. So before writing any Java, I wrote a glossary: a `CONTEXT.md` at the repo root.

Two rules kept it honest.

**Be opinionated.** When several words mean the same thing, pick one and list the losers under an `_Avoid_` line. So:

- **Fair Value** - the model's predicted price for a set of inputs. *Avoid: fair market value, calculated price, FairValue.*
- **Valuation** - the complete assessment for one symbol at one moment. *Avoid: stock valuation.*
- **Valuation Verdict** - the OVERVALUED/UNDERVALUED judgment plus the percentage gap. *Avoid: "valued", diff, the ratio sense of "valuation".*

Three concepts, three names, and the collisions are gone. I did the same across the domain - my four earnings cases became **Trailing, Forward, Blended, Max Forward**. The prototype's code called one of them `future` while its own display label said "Forward." I killed `future`. When the code contradicts itself about a name, that's the language crying for help.

**Only include terms unique to the domain.** A glossary that defines "timeout" and "cache" is just noise. This is a dictionary of the *business*, not of programming. Keep each definition to a sentence or two - what it *is*, not what it does. It's a glossary, not a spec.

## Encoding the rules as vocabulary

The part I didn't expect to love: a good glossary lets you write *constraints* as language, where everyone can see them.

- **Dividend Yield is not a Model input.** The model consumes the dividend *amount*; the yield is just displayed. Writing that into the glossary stops some future me from "fixing" the model to use yield.
- **A Watchlist is not a portfolio.** It tracks symbols; it holds no positions or quantities. That one sentence fences off a whole category of scope creep.
- **A Narrative explains a Valuation; it never produces or alters the numbers.** That's my guardrail for where AI is allowed to touch the system, stated as a definition rather than buried in an architecture doc.

These aren't comments that rot. They're the agreed meaning of the words, and the code that uses the words inherits the constraint.

## The honest trade-off

This has a cost, and I want to be straight about it. Sitting down to argue with yourself about nouns feels slow and a little bureaucratic, especially on a solo project where "just write the code" is right there. And there's a real failure mode: glossary sprawl, where you define every word until the thing becomes an unmaintained data dictionary nobody reads.

The discipline that saves it is the two rules above - be opinionated, and only capture domain-specific terms - plus a third: you have to actually maintain it, or it lies to you. For most throwaway scripts, this would be overkill. For a system whose entire job is to be *right* about money, naming is not decoration. It's a safety rail, and it's the cheapest one you'll ever install.

The payoff shows up the moment I start the Java: `FairValue`, `ValuationVerdict`, `EarningsScenario`, and `ProjectedDividend` become real types. A `Yield` can no longer be passed where a `Price` is expected. Float-soup dies not because I was careful, but because the floats finally have names.

*Next week: the architecture that lets this exact domain run as a Spring app today and as AWS Lambda functions tomorrow without the core changing - hexagonal ports and adapters, a modular monolith, and why I said no to microservices.*
