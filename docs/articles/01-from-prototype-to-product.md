# From Prototype to Product: Why I'm Rewriting a Working Python App in Java

I have a Python app that works. It scrapes market data, runs a regression, and tells me whether the S&P 500 looks over- or under-valued. It has served me well.

I'm rewriting the whole thing in Java.

If your first reaction is "why would you rewrite something that works, in a *more verbose* language, for a valuation model a data scientist would call trivial?" - good. That was my reaction too. So let me make the honest case, because the answer changed how I think about choosing tools.

## The prototype did its job - and that job is over

The Python version is a data-science prototype, and as a prototype it was excellent. pandas made wrangling decades of Shiller data trivial. scikit-learn fit the model in three lines. The whole point was to answer one question: *can a simple linear relationship between earnings, dividends, and treasury yields estimate fair value?*

The answer turned out to be yes. And here's the punchline that reframed everything: the "model" is **four numbers**. An intercept and three coefficients. Once it's fit, valuing the market is four multiply-adds. There is no machine learning to speak of - there's a line of best fit that a spreadsheet could hold.

Which means the hard part was never the model. The hard part is everything *around* it. And that's a different problem than the one Python solved.

## The problem changed shape

The prototype answered "can I find a model?" The question now is: **"can I run this correctly, continuously, and safely as a system I actually rely on?"** That is not a data-science question. It's a production-engineering question, and my prototype is visibly failing it. I don't have to speculate - the failure modes are right there in my own code:

**Correctness.** My valuation function takes bare floating-point numbers, positionally:

```
Coefficients("S&P 500", intercept, treasury, earnings, dividend, date)
```

Swap two of those arguments and nothing complains - you just get a wrong answer about money. When a scrape hiccups, a `NaN` sails straight through the arithmetic into the result. For a system whose entire purpose is to be *right* about valuations, "trust me, the arguments are in the correct order" is not a foundation.

**Resilience.** The app depends on a dozen flaky, unofficial data sources - scraped HTML pages that change layout without warning. One source failing takes down the whole refresh. There's no retry, no fallback to the last good value, no circuit breaker. It's one bad afternoon at a website I don't control away from silence.

**Operational maturity.** My "scheduler" orders its steps by *sleeping*:

```
next_run_time = now + timedelta(seconds=5)   # hope the fetch finished
next_run_time = now + timedelta(seconds=10)  # hope again before computing
```

That's a race condition wearing a schedule's clothes. There are two caching layers that don't know about each other. No health checks. No metrics. No tracing.

**Maintainability.** All of it lives in one ~230-line module where scraping, scheduling, caching, HTTP routing, and business logic are fused together. It's already a small ball of mud, and mud only grows.

None of this is a knock on Python. These are the natural artifacts of a prototype - you move fast and don't build the scaffolding, *because it's a prototype*. The mistake would be pretending a prototype is a product.

## Matching the tool to the phase

So the real reason for the rewrite isn't "Java is better." It's this:

> **The tool should match the phase of the problem. The problem graduated.**

The product phase's demands - compile-time guarantees that catch a swapped argument before it becomes a wrong number, declarative resilience for unreliable inputs, real concurrency for fan-out ingestion and a live-updating frontend, and enforced module boundaries so the thing doesn't rot - are exactly what the JVM and the Spring ecosystem treat as first-class. A strong static type system lets me model the domain so that a `Yield` can't be mistaken for a `Price` and a `Valuation` is an object with meaning, not a tuple of floats. That's not ceremony; for financial code, it's the safety rail.

Python was the right tool for discovering the model. The JVM is the right tool for running it as a service. Rewriting is the graduation from one to the other - and I think being able to say *why* you'd move a system from one platform to another, honestly, including what the old platform did well, is worth more than any single framework on a resume.

## What's next

Over the coming weeks I'm going to document this rewrite decision by decision: the domain modeling that kills the float-soup, why I chose a modular monolith over microservices, how I reimplemented a scikit-learn model in Java (spoiler: it's four numbers), how I'm making a dozen flaky scrapers trustworthy, and eventually how the whole thing goes serverless on AWS.

If you've ever inherited a prototype that outgrew itself - or written one - I'd love to hear how you drew the line between "good enough to explore" and "good enough to depend on."

*Next week: sharpening the language - how a domain glossary turned a pile of ambiguous "data" into a vocabulary, and why "float-soup" is a correctness bug, not a style nit.*
