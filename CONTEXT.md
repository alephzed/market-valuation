# Market Valuation

The domain language for a system that estimates whether a market index or equity is over- or under-priced, by comparing its market price against a model-predicted price derived from earnings, dividends, and treasury yields.

## Language

### Valuation

**Fair Value**:
The model's predicted price for a given set of inputs (treasury yield, dividend, earnings). A single number.
_Avoid_: fair market value, calculated price, FairValue

**Valuation**:
The complete assessment produced for one symbol at one point in time, bundling the earnings scenarios and their verdicts.
_Avoid_: stock valuation

**Valuation Verdict**:
The OVERVALUED or UNDERVALUED judgment for a symbol, plus the percentage gap between its market price and its Fair Value.
_Avoid_: "valued", diff, the price/fair-value ratio sense of "valuation"

**Narrative**:
A natural-language explanation of a Valuation, generated from its numbers; it explains the Valuation, it never produces or alters them.
_Avoid_: commentary, summary, insight, analysis

**Earnings Scenario**:
A choice of which earnings figure feeds the model; each scenario yields its own Fair Value and Valuation Verdict within a Valuation.
_Avoid_: earnings model, earnings basis, StockEarningsModel

**Trailing**:
The last twelve months of actual reported earnings.
_Avoid_: current earnings

**Forward**:
The next twelve months of estimated earnings.
_Avoid_: future

**Blended**:
A mix of the most recent actual and nearest estimated quarters, straddling the current-quarter boundary.

**Max Forward**:
The furthest-out twelve-month window of estimated earnings.
_Avoid_: max

### Model Calibration

**Valuation Model**:
The linear relationship mapping treasury yield, dividend, and earnings to a Fair Value.
_Avoid_: regression, ML model, the algorithm

**Coefficients**:
The four fitted numbers (an intercept plus one per input) that parameterize the Valuation Model.
_Avoid_: weights, params

**Calibration**:
Fitting the Coefficients by ordinary least squares over historical data. Deterministic curve-fitting, not machine learning.
_Avoid_: training, fitting, "the ML step"

**Shiller Data**:
The historical monthly dataset (price, dividend, earnings, 10-year treasury yield) that Calibration fits against.
_Avoid_: ie_data, the excel file

### Market Data

**Treasury Yield**:
The yield on the 10-year US treasury; a primary Model input.
_Avoid_: rate, Rate GS10, US10Y, interest rate

**Treasury Curve Forecast**:
A table of projected future treasury yields by month, used to supply the Treasury Yield for a future-dated scenario.
_Avoid_: treasury curve, the forecast

**Scenario Date**:
The point in time a scenario's inputs are evaluated at: today for Trailing, a future month for Forward, Max Forward, and Blended.
_Avoid_: earnings date, valuation date

**Dividend**:
The current per-share dividend amount paid by a symbol; a Model input.
_Avoid_: payout, distribution

**Dividend Yield**:
The Dividend as a percentage of price. Displayed market data, not a Model input.
_Avoid_: yield (bare)

**Dividend Growth**:
The annual rate at which the Dividend grows, used to project the Dividend to a Scenario Date.
_Avoid_: growth, growth rate

**Projected Dividend**:
The Dividend compounded forward by Dividend Growth from its reported date to a Scenario Date; the value each scenario feeds the Model.
_Avoid_: current dividend, future dividend, compounded dividend

**Symbol**:
The identifier for the index or equity being valued (e.g. `^GSPC` for the S&P 500).
_Avoid_: ticker, stock, instrument

**Market Price**:
The current traded price of a Symbol, compared against Fair Value to produce the Valuation Verdict.
_Avoid_: open, quote price, spot

**Stock Quote**:
A live price reading for a Symbol, carrying its Market Price.
_Avoid_: quote (bare)

**Market Data**:
The bundle of scraped market fundamentals for a Symbol (PE Ratio, Dividend Yield, Dividend, Dividend Growth, Treasury Yield).
_Avoid_: data, market values

**PE Ratio**:
Price-to-earnings ratio; a displayed fundamental, not a Model input.
_Avoid_: P/E

### Identity

**Watchlist**:
A user's set of Symbols to be valued on their behalf. A Watchlist tracks Symbols only; it does not hold positions or quantities.
_Avoid_: portfolio

**Alert**:
A notification raised when a Symbol's Valuation Verdict crosses a user-configured threshold (e.g. crossing into OVERVALUED, or the gap exceeding a set percentage).
_Avoid_: notification, trigger
