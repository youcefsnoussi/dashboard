# AI forecast work — branch `ai-forecast-gpu-notebook`

Two additions on top of the base app, kept on this branch only — nothing here touches
`master`.

## 1. "Prévision IA" page inside the app

New menu entry under Statistique. Reads precomputed forecasts from a new
`ai_forecast` schema (`demand_test`, `demand_metrics`) in your local Postgres — it
never trains anything live and never touches a remote server.

To run the app on this branch, set a local DB password as an environment variable
(the file no longer hardcodes one — see `src/main/resources/application.properties`):

```bash
export SIM_DB_PASSWORD='your-local-postgres-password'
# optional, only if your local setup differs from the defaults:
export SIM_DB_URL='jdbc:postgresql://localhost:5432/commercial?options=-c%20DateStyle%3DISO,DMY'
export SIM_DB_USER='postgres'

./mvnw spring-boot:run
```

To (re)generate the `ai_forecast` tables from your own local database, run
`notebooks/data`'s extraction query (see the notebook, section 1) against your local
copy, then the training step from `train_sim_forecast.py` (not included on this
branch — ask if you want it added), and load the two output CSVs into
`ai_forecast.demand_test` / `ai_forecast.demand_metrics`.

## 2. `notebooks/sim_demand_forecast_gpu.ipynb`

GPU-accelerated grid search (XGBoost, `TimeSeriesSplit`) compared honestly against
the Random Forest currently deployed in the app, and against two naive baselines.
Bundled with its data (`notebooks/data/sim_category_daily.csv` — the same real,
category-aggregated `facture_detail` history the deployed model was trained on).

**Open directly in Colab:**

```
https://colab.research.google.com/github/youcefsnoussi/dashboard/blob/ai-forecast-gpu-notebook/notebooks/sim_demand_forecast_gpu.ipynb
```

Since the repo is private, Colab will prompt you to authorize GitHub access the first
time — accept with your own GitHub account (must have at least read access to this
repo). Then: **Runtime → Change runtime type → T4 GPU**, and run all cells top to
bottom. The data file travels with the notebook via `git clone` under the hood, so
there's no separate upload step.

The notebook is honest about one thing up front: this dataset (~10.5k rows, daily
category aggregates) is small enough that the GPU won't show a dramatic speed
difference over CPU. It's wired for GPU because the natural next step is re-running
this same search on raw transaction-level `facture_detail` rows (potentially
millions), where it actually matters — the grid search result itself stands
regardless of hardware.
